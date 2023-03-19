<template>
    <button @click="scrollToTop" class="back-to-top-btn">Наверх</button>
</template>
    
<script>

export default {
    name: 'BackToTop',

    methods: {
        scrollToTop() {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
        }
    }
}
</script>
        
<style scoped>
.back-to-top-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border-radius: 5px;
  cursor: pointer;
}
</style>