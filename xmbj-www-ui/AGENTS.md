# Agent Development Guide

This file contains essential guidelines for AI coding agents working on the PonyNotes Web/Desktop project.

## Project Stack

- **Framework**: Nuxt 3 (Vue 3 + SSR)
- **Styling**: Tailwind CSS
- **Package Manager**: pnpm
- **TypeScript**: Enabled with Nuxt auto-generated configs
- **Backend API**: REST API at http://localhost:8080

## Build Commands

```bash
# Install dependencies
pnpm install

# Start development server (http://localhost:3000)
pnpm dev

# Production build
pnpm build

# Preview production build
pnpm preview

# Generate static site
pnpm generate

# Prepare Nuxt (auto-runs after install)
pnpm postinstall
```

**Note**: No test/lint commands are currently configured. When implementing tests, use Vitest (Nuxt 3 recommended).

## Directory Structure

```
app/
├── api/          # API client modules (user.ts, payment.ts, testUser.ts)
├── assets/       # Static assets (CSS, images)
├── components/   # Vue components (AppHeader.vue, LoginModal.vue, etc.)
├── composables/  # Vue composables (useApi.ts, useDeviceDetection.ts)
├── layouts/      # Page layouts (default.vue)
├── pages/        # Auto-routed pages (index.vue, account.vue, etc.)
├── plugins/      # Nuxt plugins (aos.client.ts)
├── types/        # TypeScript type definitions (api.ts)
└── utils/        # Utility functions (api.ts)
```

## Code Style Guidelines

### Imports

```typescript
// Nuxt auto-imports: ref, computed, onMounted, useSeoMeta, etc. (no import needed)
// Use `~` alias for app directory
import { userApi } from '~/api/user'
import type { UserEntity } from '~/types/api'
import { post, get } from '~/utils/api'
```

**Order**: External packages → Internal modules → Types (using `type` keyword for type-only imports)

### Formatting

- **Indentation**: 2 spaces
- **Quotes**: Single quotes for strings
- **Semicolons**: Required (not optional)
- **Line Length**: No strict limit, but keep readable (~100 chars)
- **Trailing Commas**: Use in multiline objects/arrays

### TypeScript

```typescript
// Always use explicit types for function parameters
export const login = (data: LoginBody) => { }

// Use interfaces for object shapes
export interface UserEntity {
  userId?: number
  username?: string
}

// Use type for unions/intersections
export type ApiResponse<T = any> = {
  code: number
  msg: string
  data: T
}

// Avoid 'any' - use generic types or 'unknown' when appropriate
```

### Vue Components

```vue
<script setup lang="ts">
// Use Composition API with <script setup>
// Imports auto-available: ref, computed, watch, onMounted, etc.

const api = useApi()
const count = ref(0)

const fetchData = async () => {
  const res = await api.user.getInfo()
  console.log(res)
}
</script>

<template>
  <!-- Use Tailwind utility classes -->
  <div class="max-w-[1100px] mx-auto px-6">
    <button @click="fetchData" class="bg-[#FF4D00] text-white px-5 py-2 rounded-lg">
      Click
    </button>
  </div>
</template>
```

### Naming Conventions

- **Files**: camelCase for utilities, PascalCase for components (`LoginModal.vue`, `useApi.ts`)
- **Variables/Functions**: camelCase (`getUserInfo`, `isMobile`)
- **Components**: PascalCase (`AppHeader`, `LoginModal`)
- **Constants**: SCREAMING_SNAKE_CASE (`API_BASE_URL`)
- **Types/Interfaces**: PascalCase (`UserEntity`, `ApiResponse`)

### API Integration

```typescript
// All API calls through composables
const api = useApi()

// User APIs
await api.user.login({ username, password })
await api.user.registerUser({ phone, code })

// Test User CRUD
await api.testUser.getUserList()
await api.testUser.saveUser({ username: 'test' })

// Payment
await api.payment.createPayment({ amount: 99, paymentType: 'alipay', userInfo: 'user123' })
```

### Error Handling

```typescript
// Use try-catch for async operations
const login = async () => {
  try {
    const res = await api.user.login(credentials)
    if (res.code === 200) {
      // Handle success
    }
  } catch (error) {
    console.error('Login failed:', error)
    // Handle error gracefully
  }
}
```

### SEO Optimization

```vue
<script setup lang="ts">
// Use useSeoMeta for page-specific SEO
useSeoMeta({
  title: 'Page Title',
  description: 'Page description for search engines',
  keywords: 'vue3, nuxt3, seo'
})
</script>
```

### Responsive Design

```vue
<!-- Mobile-first with Tailwind breakpoints -->
<div class="grid grid-cols-1 md:grid-cols-3 gap-4">
  <!-- 1 column on mobile, 3 on desktop (md: 768px+) -->
</div>

<nav class="md:hidden">
  <!-- Show only on mobile -->
</nav>

<nav class="hidden md:block">
  <!-- Show only on desktop -->
</nav>
```

### Composables Pattern

```typescript
// Export function that returns reactive state/methods
export const useDeviceDetection = () => {
  const isMobile = ref(false)
  
  const checkDevice = () => {
    if (process.client) {
      isMobile.value = window.innerWidth < 768
    }
  }
  
  onMounted(() => {
    checkDevice()
    window.addEventListener('resize', checkDevice)
  })
  
  onUnmounted(() => {
    window.removeEventListener('resize', checkDevice)
  })
  
  return { isMobile }
}
```

## Important Notes

- **Auto-Routing**: Files in `app/pages/` automatically become routes (no manual config needed)
- **SSR**: All pages are server-side rendered by default (SEO-friendly)
- **API Base URL**: Default is `http://localhost:8080` (change in `app/utils/api.ts`)
- **No Comments Policy**: Code should be self-documenting; avoid unnecessary comments
- **Client-Only Code**: Use `process.client` check or `.client.ts` suffix for browser-only code

## Git Workflow

- No pre-commit hooks configured currently
- Follow conventional commit messages when possible
- Test locally with `pnpm dev` before committing
