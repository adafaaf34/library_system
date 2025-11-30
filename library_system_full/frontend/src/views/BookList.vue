<template>
  <div>
    <h2>Books</h2>
    <div v-for="inv in inventory" :key="inv.inventory_id">
      <div>{{inv.name}} ({{inv.isbn}}) - {{inv.status}}</div>
      <button @click="borrow(inv.inventory_id)" :disabled="inv.status !== 'AVAILABLE'">Borrow</button>
      <button @click="ret(inv.inventory_id)" :disabled="inv.status !== 'BORROWED'">Return</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return { inventory: [] }
  },
  async mounted() {
    console.log("BookList mounted")
    this.loadInventory()
  },
  methods: {
    async loadInventory() {
      try {
        const r = await axios.get('http://localhost:8080/api/books/inventory')
        this.inventory = r.data
      } catch(e) {
        console.error("取得書本清單失敗:", e)
      }
    },
    async borrow(id) {
      console.log("borrow clicked, id =", id) // <-- 加上這行測試
      const token = localStorage.getItem('jwt')
      if (!token) { alert("請先登入才能借書！"); return }

      try {
        await axios.post('http://localhost:8080/api/borrow/do', { inventoryId: id }, {
          headers: { Authorization: `Bearer ${token}` }
        })
        alert('借閱成功')
        this.loadInventory()
      } catch(e) {
        alert(e.response?.data || e.message)
      }
    },
    async ret(id) {
      console.log("return clicked, id =", id) // <-- 這裡也加測試
      const token = localStorage.getItem('jwt')
      if (!token) { alert("請先登入才能還書！"); return }

      try {
        await axios.post('http://localhost:8080/api/borrow/return', { inventoryId: id }, {
          headers: { Authorization: `Bearer ${token}` }
        })
        alert('還書成功')
        this.loadInventory()
      } catch(e) {
        alert(e.response?.data || e.message)
      }
    }
  }
}
</script>
