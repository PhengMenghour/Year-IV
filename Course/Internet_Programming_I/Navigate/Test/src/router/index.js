import { createRouter, createWebHistory } from 'vue-router'
import Page1 from '@/components/Page1.vue'
import Page2 from '@/components/Page2.vue'
import Page3 from '@/components/Page3.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/page2',
      component: Page2,
      name: 'page2'
    },
    {
      path: '/page3',
      component: Page3,
      name: 'page3'
    },

  ],
})

export default router
