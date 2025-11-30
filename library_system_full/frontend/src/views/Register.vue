<template>
  <div>
    <h2>Register</h2>
    <input v-model="phone" placeholder="phone" />
    <input v-model="password" type="password" placeholder="password" />
    <input v-model="name" placeholder="name" />
    <button @click="onRegister">Register</button>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() { return { phone:'', password:'', name:'' } },
  methods: {
    async onRegister() {
      try {
        await axios.post('/auth/register', { phone: this.phone, password: this.password, userName: this.name })
        alert('registered')
      } catch (e) { alert(e.response?.data || e.message) }
    }
  }
}
</script>
