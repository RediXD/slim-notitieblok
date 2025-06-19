<template>
  <div class="login-container">
    <h2>🔐 Inloggen</h2>
    <input v-model="gebruikersnaam" placeholder="Gebruikersnaam" />
    <input v-model="wachtwoord" type="password" placeholder="Wachtwoord" />
    <button @click="login">Inloggen</button>
    <p v-if="fout" class="error">{{ fout }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      gebruikersnaam: '',
      wachtwoord: '',
      fout: ''
    };
  },
  methods: {
    async login() {
      this.fout = '';

      if (!this.gebruikersnaam || !this.wachtwoord) {
        this.fout = 'Vul gebruikersnaam en wachtwoord in.';
        return;
      }

      try {
        const res = await axios.post('http://localhost:8080/api/login', {
          username: this.gebruikersnaam,
          password: this.wachtwoord
        });

        if (res.data.loggedIn) {
          sessionStorage.setItem('ingelogd', 'true');
          localStorage.setItem('userId', res.data.userId); 
          this.$emit('loginSuccess');
        } else {
          this.fout = 'Onjuiste gebruikersnaam of wachtwoord';
        }
      } catch (e) {
        this.fout = 'Kan geen verbinding maken met de server';
        console.error('Login fout:', e);
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: auto;
  margin-top: 10vh;
  padding: 2rem;
  background: white;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}
input {
  display: block;
  width: 100%;
  margin: 1rem 0;
  padding: 0.5rem;
}
button {
  padding: 0.5rem 1rem;
  background: #3b82f6;
  color: white;
  border: none;
  border-radius: 4px;
}
.error {
  color: red;
  margin-top: 0.5rem;
}
</style>




