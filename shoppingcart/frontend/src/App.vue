
<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import ProductList from './components/ProductList.vue'
import CartView from './components/CartView.vue'

const products = ref([])
const items = ref([])
const total = ref(0)

// READ products and cart
async function load() {
  products.value = (await axios.get('/api/products')).data

  const cart = (await axios.get('/api/cart')).data
  items.value = cart.items
  total.value = cart.total
}

// CREATE product
async function addProduct(product) {
  await axios.post('/api/products', product)
  await load()
}

// UPDATE product
async function editProduct(id, product) {
  await axios.put('/api/products/' + id, product)
  await load()
}

// DELETE product
async function deleteProduct(id) {
  await axios.delete('/api/products/' + id)
  await load()
}

// ADD TO CART
async function addToCart(id) {
  await axios.post('/api/cart/add/' + id)
  await load()
}

// UPDATE CART QUANTITY using @RequestBody
async function updateCart(id, quantity) {
  await axios.put('/api/cart/' + id, { quantity: quantity })
  await load()
}

// REMOVE FROM CART
async function removeCart(id) {
  await axios.delete('/api/cart/' + id)
  await load()
}

onMounted(load)
</script>

<template>
  <div class="container">
    <h1>Shopping Cart</h1>

    <ProductList
      :products="products"
      @addProduct="addProduct"
      @editProduct="editProduct"
      @deleteProduct="deleteProduct"
      @addToCart="addToCart"
    />

    <CartView
      :items="items"
      :total="total"
      @update="updateCart"
      @remove="removeCart"
    />
  </div>
</template>

<style scoped>
.container {
  max-width: 900px;
  margin: 30px auto;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  color: darkblue;
  margin-bottom: 30px;
}
</style>
