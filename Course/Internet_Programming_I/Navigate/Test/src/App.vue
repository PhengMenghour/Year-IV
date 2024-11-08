<script setup>
import { RouterLink, RouterView } from 'vue-router'
export default {
  methods: {

  }
}
</script>

<template>
  <div class="navbar">
    <span @click="goToPage('/page2')">Go Page 2</span> |
    <span @click="goToPage('/page3')">Go Page 3</span>
  </div>
  <div class="container">Page 1</div>
  <router-view/>
</template>

<style scoped></style>
