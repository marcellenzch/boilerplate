import { defineConfig } from 'vite';
import * as path from 'path';
import react from '@vitejs/plugin-react-swc';
import checker from 'vite-plugin-checker';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    checker({
      // e.g. use TypeScript check
      typescript: true,
    }),
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  preview: {
    port: 5173,
  },
});
