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
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()
    const phone = ref('')
    const password = ref('')

  const onLogin = async () => {
    try {
      const r = await axios.post('http://localhost:8080/api/auth/login', {
        phone: phone.value,
        password: password.value
      })
      localStorage.setItem('jwt', r.data.token)
      alert('登入成功！')
      router.push('/books') 
    } catch(e) {
      alert(e.response?.data || e.message)
    }
  }

    return { phone, password, onLogin }
  }
}
</script>
