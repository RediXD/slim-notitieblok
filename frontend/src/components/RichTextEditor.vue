<template>
  <div class="editor-wrapper">
    <div ref="editor" class="quill-editor"></div>
  </div>
</template>

<script>
import Quill from 'quill';

export default {
  props: ['modelValue'],
  emits: ['update:modelValue'],
  mounted() {
    this.quill = new Quill(this.$refs.editor, {
      theme: 'snow',
      modules: {
        toolbar: [
          ['bold', 'italic', 'underline'],
          [{ list: 'ordered' }, { list: 'bullet' }],
          ['link', 'image']
        ]
      }
    });

    this.quill.root.innerHTML = this.modelValue || '';

    this.quill.on('text-change', () => {
      this.$emit('update:modelValue', this.quill.root.innerHTML);
    });
  }
};
</script>

<style scoped>
.editor-wrapper {
  border: 1px solid #ccc;
  border-radius: 6px;
  margin: 0.5rem 0;
}
.ql-editor {
  min-height: 100px;
  padding: 8px;
}
.ql-toolbar {
  background: #f5f5f5;
}
</style>



