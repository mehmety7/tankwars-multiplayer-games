<!-- app.vue -->
<template>
  <div class="parent">
    <h1>Parent Component</h1>
    <!-- Provide/Inject -->
    <button @click="greetingMessageStatus = !greetingMessageStatus">
      Change Greeting Message
    </button>
    Provide/Inject
    <hr />
    <!-- Refs -->
    <button style="color: red" @click="callChildComponentMethod">
      Call Child Component Method
    </button>
    Refs

    <!-- Emit & Event -->
    <hr />
    <div v-show="isVisible" class="alert">This is ParentComponent!</div>

    <br /><br />
    <div>
      <ChildComponent
        componentTitle="Child Component"
        ref="childComponent"
        @raise-parent-alert="raiseAlert"
      />
    </div>
  </div>
</template>
<script setup>
//Provide/Inject
const greetingMessageStatus = ref(true);
provide("greetingMessageStatus", greetingMessageStatus);

//Refs
const childComponent = ref(null);
function callChildComponentMethod() {
  childComponent.value.raiseAlert();
}

//Emit & Event
const isVisible = ref(false);
function raiseAlert() {
  isVisible.value = !isVisible.value;
}
</script>
<style scoped>
.parent {
  margin: 50px;
  padding: 50px;
  padding-bottom: 50px;
  background-color: blanchedalmond;
  width: 700px;
}
.alert {
  padding: 20px;
  background-color: plum;
  color: white;
  margin-bottom: 15px;
}
</style>
