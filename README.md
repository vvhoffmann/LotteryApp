# LotteryApp

A small backend service that stores lottery tickets submitted by users and schedules them for the next weekly draw.

---

## Krótkie podsumowanie

* **Cel aplikacji** – przyjmowanie od użytkownika zestawów 6 unikalnych liczb (1‑99) i zapisywanie ich w bazie jako
  *Ticket* wraz z unikalnym identyfikatorem oraz datą najbliższego losowania.
* **Najbliższe losowanie** – sobota, godzina 12:00. Jeżeli aktualny dzień to sobota < 12:00, losowanie odbędzie się tego
  samego dnia; w przeciwnym razie − w następną sobotę.
* **Walidacja wejścia** – liczba liczb = 6, każda w zakresie 1‑99. W razie błędu zwracany jest komunikat z przyczyną.
* **Składowanie danych** – MongoDB (kolekcja `ticket`).

---

## Stos technologiczny

| Kategoria    | Wykorzystana technologia                            |
|--------------|-----------------------------------------------------|
| Runtime      | Java 17                                             |
| Framework    | Spring Boot 3.5.0                                   |
| Persystencja | Spring Data MongoDB                                 |
| Budowanie    | Maven 3.9                                           |
| Kontenery    | Docker, Docker Compose                              |
| Testy        | JUnit 5, Mockito, TestContainers, AssertJ, WireMock |
| Inne         | Lombok                                              |

---

## Architektura (wysoki poziom)

```
┌────────────────────────────────────────┐
│           interfejs REST/API           │ (warstwa wejścia – TBD)
└────────────────────────────────────────┘
                 │                      
                 ▼                      
┌────────────────────────────────────────┐
│        Warstwa domenowa (core)        │
│                                        │
│ NumberReceiverFacade                   │
│  ├── NumberValidator                   │
│  ├── DrawDateGenerator                 │
│  └── HashGenerator                     │
│                                        │
│ Encje: Ticket                          │
└────────────────────────────────────────┘
                 │                      
                 ▼                      
┌────────────────────────────────────────┐
│  Warstwa infrastruktury/persystencji   │
│  (Spring Data – MongoDB)               │
│  • TicketRepository                    │
└────────────────────────────────────────┘
```

* **Rozdzielenie odpowiedzialności** – zasady biznesowe znajdują się w pakiecie `pl.lotto.domain.*`; zależności
  zewnętrzne są wstrzykiwane przez konstruktor `NumberReceiverFacade`, co ułatwia testowanie.
* **Wzorzec „Facade”** – pojedynczy punkt wejścia do funkcjonalności modułu `numberreceiver` upraszcza integrację z
  innymi modułami lub warstwą REST.
* **Database‑first** – domena definiuje interfejs `TicketRepository`, a konkretna implementacja dostarcza Spring Data.
* **Konteneryzacja** – plik `docker-compose.yml` uruchamia jednocześnie MongoDB (z GUI mongo‑express) i obraz zbudowany
  z Dockerfile aplikacji.

---

## Uruchamianie lokalnie

1. Sklonuj repozytorium i zbuduj artefakt:

   ```bash
   mvn clean package -DskipTests
   ```
2. Wystartuj stack:

   ```bash
   docker compose up --build
   ```

    * Aplikacja: `http://localhost:8080`
    * mongo‑express: `http://localhost:8081`

> **Uwaga:** Pierwsze uruchomienie może potrwać, ponieważ pobierane są obrazy bazowe.

