<script setup>
import { onMounted } from 'vue'
import { useCartStore } from '../stores/cartStore'
import CartView from '../components/CartView.vue'

const store = useCartStore()
onMounted(() => store.load())
</script>

<template>
  <CartView
    :items="store.items"
    :total="store.total"
    @update="store.updateCart"
    @remove="store.removeCart"
  />
</template>
