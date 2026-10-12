# Shopping Cart with simple Pinia and Vue Router

## What changed
- `frontend/src/stores/cartStore.js`: Pinia state (`products`, `items`, `total`) and Axios CRUD actions, moved from `App.vue`.
- `frontend/src/router/index.js`: `/` Products page, `/cart` My Cart page.
- `frontend/src/views/ProductsPage.vue` and `CartPage.vue`: connect the store to the original child components.
- `frontend/src/App.vue`: navigation and `<RouterView />`.
- `frontend/src/main.js`: registers Pinia and Vue Router.
- Backend: removed the duplicate cart PUT mapping; kept `@RequestBody CartItem` to match the Axios JSON body.

## Requirements
- Java and Maven (or use the included Maven wrapper)
- MongoDB running locally on port 27017
- Node.js matching the version required in `frontend/package.json` (Node 22.18+ or 24.12+)

## Run
1. Start local MongoDB. Verify `src/main/resources/application.properties` points to `mongodb://localhost:27017/shoppingcart`.
2. Terminal 1 (project root): `chmod +x mvnw` (macOS/Linux if needed), then `./mvnw spring-boot:run`. Windows: `mvnw.cmd spring-boot:run`.
3. Terminal 2: `cd frontend`, `npm install`, `npm run dev`.
4. Open `http://localhost:5173/` for Products; use the My Cart link or open `http://localhost:5173/cart`.

Vite proxies `/api` to Spring Boot on port 8080. MongoDB is used by Spring Boot, not directly by Vue.

## Learning flow
Child component -> emits event -> Page view -> Pinia store action -> Axios -> Spring Boot -> MongoDB.
Backend response -> store reactive state -> page props -> child component.
