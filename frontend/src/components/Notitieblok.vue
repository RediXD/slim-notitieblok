<template>
  <div class="notepad-wrapper">
    <div class="topbar">
      <h1>📜 Mijn Notitieblok</h1>
      <button @click="uitloggen" class="logout-btn">🔓 Uitloggen</button>
    </div>

    <input id="date" type="date" lang="en" v-model="nieuwe.deadline" />


    <div class="filters">
      <button @click="setFilter('all')">📋 All</button>
      <button @click="setFilter('active')">🟢 Active</button>
      <button @click="setFilter('archived')">📁 Archived</button>
    </div>

    <form class="note-form" @submit.prevent="voegToe">
      <label for="title">Title</label>
      <input id="title" class="field-title" v-model="nieuwe.titel" placeholder="✍️ Note title" required />

      <label for="tag">Label</label>
      <input id="tag" class="field-tag" v-model="nieuwe.label" placeholder="🏷️ Label (e.g. school, work)" />

      <label for="date">Deadline</label>
      <input id="date" type="date" lang="en" v-model="nieuwe.deadline" placeholder="yyyy-mm-dd" />

      <label for="content">Content</label>
      <textarea id="content" v-model="nieuwe.inhoud" placeholder="📋 Note content..."></textarea>

      <label for="image">Image</label>
      <input id="image" type="file" @change="onFileChange" title="Choose a file" />

      <button class="btn-save" type="submit">📂 Save note</button>
    </form>

    <div class="note-list">
      <div v-for="(n, index) in gefilterdeNotities" :key="n.id + '-' + index" class="note-card">
        <h3>{{ n.titel }}</h3>
        <div v-html="n.inhoud"></div>
        <img v-if="n.afbeeldingUrl" :src="n.afbeeldingUrl" style="max-width: 100%; margin-top: 1rem" />
        <small>{{ getLabelEmoji(n.label) }} {{ n.label }} | 📅 {{ n.deadline }}</small>
        <div v-if="!n.gearchiveerd">
          <button @click="archiveer(n.id)">📥 Archive</button>
        </div>
        <div v-else>
          <em>Archived</em>
        </div>
        <button @click="verwijder(n.id)">🗑 Delete</button>
        <button @click="exporteerPDF(n)">📄 Export PDF</button>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import jsPDF from 'jspdf';
import 'jspdf-autotable';

export default {
  data() {
    return {
      notities: [],
      nieuwe: { titel: '', inhoud: '', label: '', deadline: '' },
      geselecteerdBestand: null,
      zoekterm: '',
      filterStatus: localStorage.getItem('filterStatus') || 'all'
    };
  },
  computed: {
    gefilterdeNotities() {
      const term = this.zoekterm.toLowerCase();
      return this.notities.filter(n => {
        const matchZoek = n.titel.toLowerCase().includes(term) || (n.label || '').toLowerCase().includes(term);
        if (this.filterStatus === 'active') return matchZoek && !n.gearchiveerd;
        if (this.filterStatus === 'archived') return matchZoek && n.gearchiveerd;
        return matchZoek;
      });
    }
  },
  methods: {
    setFilter(status) {
      this.filterStatus = status;
      localStorage.setItem('filterStatus', status);
    },
    getLabelEmoji(label) {
      const mapping = {
        school: "📚",
        werk: "💼",
        persoonlijk: "🧐",
        boodschappen: "🛒"
      };
      if (!label) return "📝";
      return mapping[label.toLowerCase()] || "📝";
    },
    onFileChange(event) {
      this.geselecteerdBestand = event.target.files[0];
    },
    async haal() {
      const userId = localStorage.getItem('userId');
      if (!userId) return;
      try {
        const res = await axios.get(`http://localhost:8080/api/notities/${userId}`);
        this.notities = res.data;
      } catch (err) {
        console.error('Fout bij laden van notities:', err);
      }
    },
    async voegToe() {
      const userId = localStorage.getItem('userId');
      if (!userId) return;

      const formData = new FormData();
      formData.append('titel', this.nieuwe.titel);
      formData.append('inhoud', this.nieuwe.inhoud);
      formData.append('label', this.nieuwe.label);
      formData.append('deadline', this.nieuwe.deadline);
      if (this.geselecteerdBestand) {
        formData.append('afbeelding', this.geselecteerdBestand);
      }

      try {
        const res = await axios.post(`http://localhost:8080/api/notities/${userId}`, formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
        this.notities.unshift(res.data);
        this.nieuwe = { titel: '', inhoud: '', label: '', deadline: '' };
        this.geselecteerdBestand = null;
        this.haal();
      } catch (e) {
        console.error('Fout bij opslaan:', e);
      }
    },
    async archiveer(id) {
      await axios.put(`http://localhost:8080/api/notities/${id}/archive`);
      this.haal();
    },
    async verwijder(id) {
      if (confirm('Weet je zeker dat je deze notitie wilt verwijderen?')) {
        await axios.delete(`http://localhost:8080/api/notities/${id}`);
        this.haal();
      }
    },
    exporteerPDF(notitie) {
      const doc = new jsPDF();
      doc.setFontSize(18);
      doc.text(notitie.titel, 10, 10);
      doc.setFontSize(12);
      doc.text(`Label: ${notitie.label}`, 10, 20);
      doc.text(`Deadline: ${notitie.deadline}`, 10, 30);
      doc.text('Inhoud:', 10, 40);
      doc.text(notitie.inhoud, 10, 50);
      doc.save(`${notitie.titel}.pdf`);
    },
    uitloggen() {
      sessionStorage.removeItem('ingelogd');
      this.$emit('uitloggen');
    }
  },
  mounted() {
    this.haal();
  }
};
</script>

<style scoped>
.topbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logout-btn {
  background: #ef4444;
  color: white;
  border: none;
  padding: 0.4rem 0.8rem;
  border-radius: 5px;
}
.search-input {
  margin-bottom: 1rem;
  padding: 0.5rem;
  width: 100%;
}
.note-card {
  border: 1px solid #ccc;
  padding: 1rem;
  margin-bottom: 1rem;
}
.filters button {
  margin-right: 0.5rem;
  padding: 0.4rem 0.8rem;
}
</style>






