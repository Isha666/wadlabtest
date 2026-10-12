
<script setup>
defineProps({
  items: Array,
  total: Number
})

const emit = defineEmits(['update', 'remove'])
</script>

<template>
  <h2>My Cart</h2>

  <p v-if="items.length === 0">
    Your cart is empty.
  </p>

  <table v-else>
    <thead>
      <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Subtotal</th>
        <th>Action</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="i in items" :key="i.id">
        <td>{{ i.name }}</td>
        <td>₹{{ i.price }}</td>

        <td>
          <input
            type="number"
            min="0"
            :value="i.quantity"
            @change="emit('update', i.id, Number($event.target.value))"
          >
        </td>

        <td>₹{{ i.price * i.quantity }}</td>

        <td>
          <button @click="emit('remove', i.id)">
            Remove
          </button>
        </td>
      </tr>
    </tbody>
  </table>

  <h3>Total: ₹{{ total }}</h3>
</template>

<style scoped>
h2 {
  color: darkblue;
  margin-top: 30px;
}

input {
  width: 60px;
  padding: 6px;
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

h3 {
  text-align: right;
}
</style>
