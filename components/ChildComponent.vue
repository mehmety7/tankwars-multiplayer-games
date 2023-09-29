<!-- ChildComponent.vue -->
<template>
  <div class="child">
    <h2>{{ props.componentTitle }}</h2>

    <!-- Provide/Inject -->
    <DeepChildComponent />

    <!-- Refs -->
    <div v-show="isVisible" class="alert">This is ChildComponent!</div>

    <!-- Emit & Event -->
    <hr />
    <button style="color: red" @click="callParentComponentMethod">
      Call Parent Component Method
    </button>
    Emit & Event
  </div>
</template>
<script setup>
//Props
const props = defineProps(["componentTitle"]);

//Refs
const isVisible = ref(false);
function raiseAlert() {
  isVisible.value = !isVisible.value;
}
defineExpose({
  raiseAlert,
});

//Emit & Event
const emit = defineEmits(["raiseParentAlert"]);
function callParentComponentMethod() {
  emit("raiseParentAlert");
}
</script>
<style scoped>
.child {
  background-color: azure;
  padding: 10px;
  padding-bottom: 50px;
  width: 500px;
}
.alert {
  padding: 20px;
  background-color: #f44336; /* Red */
  color: white;
  margin-bottom: 15px;
}
</style>
