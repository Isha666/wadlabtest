import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export const useCartStore = defineStore('cart', () => {
  const products = ref([])
  const items = ref([])
  const total = ref(0)

  async function load() {
    products.value = (await axios.get('/api/products')).data
    const cart = (await axios.get('/api/cart')).data
    items.value = cart.items
    total.value = cart.total
  }

  async function addProduct(product) {
    await axios.post('/api/products', product)
    await load()
  }

  async function editProduct(id, product) {
    await axios.put('/api/products/' + id, product)
    await load()
  }

  async function deleteProduct(id) {
    await axios.delete('/api/products/' + id)
    await load()
  }

  async function addToCart(id) {
    await axios.post('/api/cart/add/' + id)
    await load()
  }

  async function updateCart(id, quantity) {
    await axios.put('/api/cart/' + id, { quantity: quantity })
    await load()
  }

  async function removeCart(id) {
    await axios.delete('/api/cart/' + id)
    await load()
  }

  return { products, items, total, load, addProduct, editProduct,
    deleteProduct, addToCart, updateCart, removeCart }
})
