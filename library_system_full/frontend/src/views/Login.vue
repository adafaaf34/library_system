<template>
  <div>
    <h2>Login</h2>
    <input v-model="phone" placeholder="phone" />
    <input v-model="password" type="password" placeholder="password" />
    <button @click="onLogin">Login</button>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() { return { phone:'', password:'' } },
  methods: {
    async onLogin() {
      try {
        const r = await axios.post('/auth/login', { phone: this.phone, password: this.password })
        localStorage.setItem('jwt', r.data.token)
        alert('login ok')
      } catch (e) { alert(e.response?.data || e.message) }
    }
  }
}
</script>
