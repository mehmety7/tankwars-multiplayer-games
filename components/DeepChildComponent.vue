<!-- DeepChildComponent.vue -->
<template>
  <div class="deepChild">
    <h3>Deep Child Component</h3>
    <h4
      style="
        color: blue;
        border: 5px;
        border: red;
        border-width: 4px;
        border-style: dashed;
        padding: 5px;
        border-radius: 15px;
      "
    >
      {{ greetingMessage }}
    </h4>
  </div>
</template>
<script setup>
const greetingMessageStatus = inject("greetingMessageStatus");
let greetingMessage = ref("Welcome!");
watch(greetingMessageStatus, (newValue) => {
  greetingMessage.value = newValue == true ? "Welcome!" : "Goodbye!";
});
</script>
<style scoped>
.deepChild {
  background-color: burlywood;
  padding: 10px;
  padding-bottom: 50px;
  margin-top: 70px;
  width: 300px;
}
</style>
