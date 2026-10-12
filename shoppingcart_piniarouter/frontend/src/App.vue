<template>
  <div class="container">
    <h1>Shopping Cart</h1>
    <nav>
      <RouterLink to="/">Products</RouterLink>
      <RouterLink to="/cart">My Cart</RouterLink>
    </nav>
    <RouterView />
  </div>
</template>

<style scoped>
.container { max-width: 900px; margin: 30px auto; font-family: Arial, sans-serif; }
h1 { text-align: center; color: darkblue; }
nav { display: flex; gap: 20px; margin: 20px 0; }
a { color: darkblue; }
.router-link-active { font-weight: bold; }
</style>
