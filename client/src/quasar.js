import Vue from 'vue'

import './styles/quasar.scss'
import 'quasar/dist/quasar.ie.polyfills'
import '@quasar/extras/roboto-font/roboto-font.css'
import '@quasar/extras/material-icons/material-icons.css'
import {
  Quasar,
  QTable,
  QTh,
  QTr,
  QTd,
  QInput,
  QSelect,
  QSeparator,
  QDialog,
  QCard,
  QCardSection,
  QCardActions,
  ClosePopup
} from 'quasar'

Vue.use(Quasar, {
  components: {
    QTable,
    QTh,
    QTr,
    QTd,
    QInput,
    QSelect,
    QSeparator,
    QDialog,
    QCard,
    QCardSection,
    QCardActions
  },
  plugins: {
  },
  directives: {
    ClosePopup
  }
})
