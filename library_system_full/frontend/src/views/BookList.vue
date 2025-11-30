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
  data() { return { inventory: [] } },
  async mounted() {
    try { const r = await axios.get('/books/inventory'); this.inventory = r.data } catch(e){ console.error(e) }
  },
  methods: {
    async borrow(id){ try{ await axios.post('/borrow/do', { inventoryId: id }); alert('borrowed'); location.reload() } catch(e){ alert(e.response?.data||e.message) } },
    async ret(id){ try{ await axios.post('/borrow/return', { inventoryId: id }); alert('returned'); location.reload() } catch(e){ alert(e.response?.data||e.message) } }
  }
}
</script>
