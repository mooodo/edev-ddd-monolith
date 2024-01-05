<template>
  <div id="app">
    <p>
    <router-link to="/" class="router-link-style">主页</router-link>|
    <router-link to="/customer" class="router-link-style">用户管理</router-link>|
    <router-link to="/order" class="router-link-style">订单跟踪</router-link>|
    <router-link to="/product" class="router-link-style">产品管理</router-link>
    </p>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.router-link-style {
  margin-right: 5px;
}
</style>
