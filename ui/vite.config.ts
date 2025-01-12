import path from 'node:path'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import tailwind from 'tailwindcss'
import autoprefixer from 'autoprefixer'
import dynamicImport from "vite-plugin-dynamic-import";

// https://vitejs.dev/config/
export default defineConfig({
  css: {
    postcss: {
      plugins: [tailwind(), autoprefixer()],
    }
  },
  assetsInclude: "**/*.swf",
  plugins: [vue(), dynamicImport()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@files': path.resolve(__dirname, '../files'),
      '@assets': path.resolve(__dirname, './src/assets'),
    },
  },
})
