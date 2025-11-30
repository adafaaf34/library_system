<template>
  <div class="container">
    <h1>圖書館系統首頁</h1>

    <div class="top-buttons">
      <router-link to="/register" class="btn">註冊</router-link>
      <router-link to="/login" class="btn">登入</router-link>
    </div>

    <h2>書本列表</h2>

    <div v-if="books.length === 0">
      讀取中...
    </div>

    <div v-else>
      <div v-for="book in books" :key="book.inventory_id" class="book-card">
        <p><strong>{{ book.name }}</strong></p>
        <p>作者：{{ book.author }}</p>
        <p>狀態：{{ book.status }}</p>
        <button 
          @click="borrow(book.inventory_id)" 
          :disabled="book.status !== 'AVAILABLE'"
        >
          借閱
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      books: []
    };
  },
  methods: {
    async loadBooks() {
      try {
        const resp = await axios.get("http://localhost:8080/api/books/inventory");
        this.books = resp.data;
      } catch(err) {
        console.error("取得書本清單失敗:", err);
      }
    },
    async borrow(inventoryId) {
      const token = localStorage.getItem("token");

      if (!token) {
        alert("請先登入才能借書！");
        return;
      }

      try {
        await axios.post(
          `http://localhost:8080/api/borrow/do`,
          { inventoryId },
          { headers: { Authorization: "Bearer " + token } }
        );
        alert("借閱成功！");
        this.loadBooks();
      } catch (err) {
        alert("借閱失敗：" + err.response?.data?.message || err.message);
      }
    }
  },
  mounted() {
    this.loadBooks();
  }
};
</script>

<style>
.container { width: 600px; margin: auto; padding: 20px; }
.top-buttons { margin-bottom: 20px; }
.btn { padding: 8px 12px; border: 1px solid black; margin-right: 10px; text-decoration: none; }
.book-card { border: 1px solid #ccc; padding: 12px; margin-bottom: 15px; }
</style>
