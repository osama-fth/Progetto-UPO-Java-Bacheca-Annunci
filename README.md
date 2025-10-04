# 📌 Bacheca Annunci - Progetto Java

![Java](https://img.shields.io/badge/Java-21-orange)
![Swing](https://img.shields.io/badge/Swing-GUI-blue)
![JUnit](https://img.shields.io/badge/JUnit-5.0-green)

Applicazione Java per la gestione di una bacheca di annunci (vendita/acquisto), con interfaccia grafica Swing e CLI.

## 📝 Descrizione

Gli utenti possono pubblicare, cercare e gestire annunci con dettagli come prezzo, tipologia e data di scadenza. I dati sono salvati/caricati da file.

## ✨ Funzionalità

- 👤 Gestione utenti (validazione email/nome)
- 📢 CRUD annunci (con validazioni)
- 🔍 Ricerca per parole chiave
- 📆 Pulizia annunci scaduti
- 💾 Persistenza su file
- 💡 Suggerimenti annunci vendita per ricerche di acquisto

## 🏗️ Architettura (MVC)

- Model: logica e dati (Annuncio, Bacheca, Utente + eccezioni)
- View: Swing e CLI
- Controller: coordinamento tra model e view

## 📂 Struttura progetto

```
Progetto-UPO-Java-Bacheca-Annunci/
├── Progetto_UPO_Java_Foutih_20054809/
│   ├── src/
│   │   ├── main/
│   │   │   └── Main.java
│   │   ├── modello/                  
│   │   ├── interfaccia/
│   │   │   ├── grafica/             
│   │   │   └── rigaDiComando/
│   │   │       └── InterfacciaRigaDiComando.java
│   │   └── annunci.txt                                   # file dati letto a runtime come "src/annunci.txt"
│   └── bin/                          
├── dist/                             
│   ├── bacheca-annunci.jar
│   └── src/
│       └── annunci.txt               
├── Makefile                          
├── MANIFEST.MF                       
└── .gitignore                        
```

## 🔧 Requisiti

- JDK 21+
- make

## 🛠️ Build ed esecuzione (Makefile)

Dalla root del repo:

- Compilazione (class in Progetto_UPO_Java_Foutih_20054809/bin):
  ```
  make compile
  ```

- Creazione JAR (in dist/):
  ```
  make jar
  ```

- Esecuzione (da dist/):
  ```
  make run
  ```

- Pulizia:
  ```
  make clean        # rimuove bin/
  make clean-all    # rimuove bin/ e dist/
  ```

Dettagli:
- Il JAR non include annunci.txt. Il Makefile copia Progetto_UPO_Java_Foutih_20054809/src/annunci.txt in dist/src per permettere la lettura a runtime (il codice usa il path "src/annunci.txt").

## 💻 Utilizzo

All’avvio scegli:
- Interfaccia grafica (Swing)
- Interfaccia a riga di comando
- Uscita

Nella GUI hai pulsanti per aggiungere/rimuovere/cercare, pulire annunci scaduti e aggiungere parole chiave; in CLI analoghe opzioni via menu testuale.

## 🧪 Test

I test JUnit 5 validano Utente, Annuncio e Bacheca. Eseguili dal tuo IDE o configura un runner JUnit esterno.

---

⭐ Progetto didattico per il corso di Programmazione ad Oggetti (Università del Piemonte Orientale).
