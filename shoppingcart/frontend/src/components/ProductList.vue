
<script setup>
import { ref } from 'vue'

defineProps({
  products: Array
})

const emit = defineEmits([
  'addProduct',
  'editProduct',
  'deleteProduct',
  'addToCart'
])

const name = ref('')
const price = ref(0)
const editId = ref(null)

function save() {
  const product = {
    name: name.value,
    price: Number(price.value)
  }

  if (editId.value) {
    emit('editProduct', editId.value, product)
  } else {
    emit('addProduct', product)
  }

  name.value = ''
  price.value = 0
  editId.value = null
}

function edit(p) {
  editId.value = p.id
  name.value = p.name
  price.value = p.price
}
</script>

<template>
  <h2>Products</h2>

  <form @submit.prevent="save">
    <input
      v-model="name"
      placeholder="Product name"
      required
    >

    <input
      v-model.number="price"
      type="number"
      min="0"
      placeholder="Price"
      required
    >

    <button type="submit">
      {{ editId ? 'Update Product' : 'Add Product' }}
    </button>
  </form>

  <table>
    <thead>
      <tr>
        <th>Name</th>
        <th>Price (₹)</th>
        <th>Actions</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="p in products" :key="p.id">
        <td>{{ p.name }}</td>
        <td>{{ p.price }}</td>
        <td>
          <button @click="edit(p)">Edit</button>

          <button @click="emit('deleteProduct', p.id)">
            Delete
          </button>

          <button @click="emit('addToCart', p.id)">
            Add to Cart
          </button>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<style scoped>
h2 {
  color: darkblue;
}

form {
  margin-bottom: 20px;
}

input {
  padding: 8px;
  margin: 5px;
  width: 150px;
}

button {
  padding: 8px;
  margin: 5px;
  cursor: pointer;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}

th, td {
  border: 1px solid gray;
  padding: 10px;
  text-align: left;
}

th {
  background-color: #eeeeee;
}
</style>
