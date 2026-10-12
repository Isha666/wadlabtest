<script setup>
import { onMounted } from 'vue'
import { useCartStore } from '../stores/cartStore'
import ProductList from '../components/ProductList.vue'

const store = useCartStore()
onMounted(() => store.load())
</script>

<template>
  <ProductList
    :products="store.products"
    @addProduct="store.addProduct"
    @editProduct="store.editProduct"
    @deleteProduct="store.deleteProduct"
    @addToCart="store.addToCart"
  />
</template>
