# 📌 Bacheca Annunci - Progetto Java

![Java](https://img.shields.io/badge/Java-21-orange)
![Swing](https://img.shields.io/badge/Swing-GUI-blue)
![JUnit](https://img.shields.io/badge/JUnit-5.0-green)

Un'applicazione Java per la gestione di una bacheca di annunci di vendita e acquisto, con supporto per interfaccia grafica e interfaccia a riga di comando.

## 📝 Descrizione

Bacheca Annunci è un'applicazione che permette agli utenti di pubblicare, cercare e gestire annunci di vendita o acquisto. Gli utenti possono registrarsi, creare annunci specificando dettagli come prezzo, tipologia e data di scadenza, e cercare annunci esistenti utilizzando parole chiave.

## ✨ Funzionalità principali

- 👤 **Gestione utenti**: Registrazione con validazione di email e nome utente
- 📢 **Gestione annunci**: Creazione, modifica e rimozione di annunci
- 🔍 **Ricerca intelligente**: Ricerca di annunci per parole chiave
- 📆 **Gestione scadenze**: Rimozione automatica di annunci scaduti
- 🔄 **Persistenza dei dati**: Salvataggio e caricamento degli annunci da file
- 💡 **Suggerimenti intelligenti**: Raccomandazioni di annunci di vendita in base alle ricerche di acquisto

## 🏗️ Architettura

Il progetto segue il pattern architetturale **Model-View-Controller (MVC)**:

- **Model**: Le classi che rappresentano i dati e la logica di business
- **View**: Le classi che gestiscono l'interfaccia utente
- **Controller**: Le classi che coordinano le interazioni tra model e view

## 📂 Struttura del progetto

```
Progetto_UPO_Java/
├── src/
│   ├── main/
│   │   └── Main.java
│   ├── modello/
│   │   ├── Annuncio.java
│   │   ├── Bacheca.java
│   │   ├── Utente.java
│   │   └── exception/
│   │       ├── AnnuncioException.java
│   │       ├── AutoreNonAutorizzatoException.java
│   │       ├── BachecaException.java
│   │       └── UtenteException.java
│   ├── interfaccia/
│   │   ├── grafica/
│   │   │   ├── InterfacciaGrafica.java
│   │   │   ├── controllo/
│   │   │   │   └── ControlloBacheca.java
│   │   │   └── vista/
│   │   │       ├── BachecaPanel.java
│   │   │       ├── ContentPanel.java
│   │   │       ├── OpsPanel.java
│   │   │       └── UtentePanel.java
│   │   └── rigaDiComando/
│   │       └── InterfacciaRigaDiComando.java
│   └── annunci.txt
└── test/
    └── modello/
        └── test/
            ├── AnnuncioTest.java
            ├── BachecaTest.java
            └── UtenteTest.java
```

## 🔧 Requisiti

- Java Development Kit (JDK) 21 o superiore
- JUnit 5 (per eseguire i test)
- IDE con supporto per Java (Eclipse, IntelliJ IDEA, ecc.)

## 🚀 Installazione e avvio

1. Clona il repository:
   ```
   git clone https://github.com/tuousername/Progetto-Java-Bacheca-annunci.git
   ```

2. Importa il progetto nel tuo IDE preferito come progetto Java esistente

3. Compila ed esegui la classe `main.Main`

4. L'applicazione mostrerà un menu per scegliere tra:
   - Interfaccia grafica
   - Interfaccia a riga di comando
   - Uscita dal programma

## 💻 Utilizzo

### Interfaccia grafica

1. Dopo il login, verrai accolto dalla schermata principale con:
   - Un pannello superiore con i pulsanti delle operazioni
   - Un pannello centrale che mostra gli annunci esistenti
   - Un pannello inferiore che mostra le informazioni dell'utente attuale

2. Utilizza i pulsanti per:
   - Aggiungere un nuovo annuncio
   - Rimuovere un annuncio esistente
   - Cercare annunci per parole chiave
   - Pulire la bacheca dagli annunci scaduti
   - Aggiungere parole chiave a un annuncio esistente

### Interfaccia a riga di comando

1. Dopo il login, verrà mostrato un menu con le operazioni disponibili:
   - Aggiungi annuncio
   - Rimuovi annuncio
   - Cerca annuncio
   - Pulisci bacheca
   - Visualizza bacheca
   - Aggiungi parola chiave ad annuncio
   - Esci

2. Segui le istruzioni a schermo per ogni operazione

## 🧪 Test

Il progetto include test unitari completi implementati con JUnit 5 per:
- Validazione della classe `Utente`
- Validazione della classe `Annuncio`
- Validazione della classe `Bacheca` e delle sue operazioni

Per eseguire i test, utilizza la funzionalità di test del tuo IDE o esegui i test JUnit direttamente.

---

⭐ **Nota**: Questo progetto è stato sviluppato come parte del corso di Programmazione ad Oggetti all'Università del Piemonte Orientale.
