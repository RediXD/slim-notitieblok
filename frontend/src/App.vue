<template>
  <div>
    <Login v-if="!isLoggedIn" @loginSuccess="isLoggedIn = true" />
    <Notitieblok v-else @uitloggen="handleLogout" />

  </div>
</template>

<script>
import Login from './components/Login.vue';
import Notitieblok from './components/Notitieblok.vue';

export default {
  components: {
    Login,
    Notitieblok
  },
data() {
  return {
    isLoggedIn: localStorage.getItem('ingelogd') === 'true'
  };
},
methods: {
  handleLogout() {
    localStorage.removeItem('ingelogd');
    this.isLoggedIn = false;
  }
}
};

</script>

<style>
body {
  margin: 0;
  background-color: #f5f5f5;
}
</style>



