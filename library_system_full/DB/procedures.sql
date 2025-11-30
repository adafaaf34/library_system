DELIMITER $$

-- 借書
CREATE PROCEDURE sp_borrow_book(
  IN p_user_id BIGINT,
  IN p_inventory_id BIGINT
)
BEGIN
  DECLARE v_status VARCHAR(20);
  DECLARE v_msg VARCHAR(255);

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SET v_msg = 'DB Error during borrow';
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_msg;
  END;

  START TRANSACTION;

  SELECT status 
    INTO v_status 
    FROM inventory 
   WHERE inventory_id = p_inventory_id 
   FOR UPDATE;

  IF v_status IS NULL THEN
    SET v_msg = 'Inventory not found';
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_msg;
  END IF;

  IF v_status <> 'AVAILABLE' THEN
    SET v_msg = CONCAT('Inventory not available: ', v_status);
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_msg;
  END IF;

  INSERT INTO borrowing_record (user_id, inventory_id, borrowing_time)
  VALUES (p_user_id, p_inventory_id, NOW());

  UPDATE inventory 
     SET status = 'BORROWED'
   WHERE inventory_id = p_inventory_id;

  COMMIT;
END $$

-- 還書
CREATE PROCEDURE sp_return_book(
  IN p_user_id BIGINT,
  IN p_inventory_id BIGINT
)
BEGIN
  DECLARE v_msg VARCHAR(255);

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    ROLLBACK;
    SET v_msg = 'DB Error during return';
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_msg;
  END;

  START TRANSACTION;

  UPDATE borrowing_record
     SET return_time = NOW()
   WHERE inventory_id = p_inventory_id
     AND user_id = p_user_id
     AND return_time IS NULL;

  -- 檢查是否真的有還書
  IF ROW_COUNT() = 0 THEN
    SET v_msg = 'No active borrowing record found for this user and inventory';
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_msg;
  END IF;

  UPDATE inventory
     SET status = 'AVAILABLE'
   WHERE inventory_id = p_inventory_id;

  COMMIT;
END $$

DELIMITER ;
