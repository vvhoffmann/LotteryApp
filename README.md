# 🎰 LotteryApp

Aplikacja backendowa do obsługi loterii. Umożliwia użytkownikom zgłaszanie swoich losów (zestawu 6 liczb) oraz przypisuje je do najbliższego losowania.

 //ang
A small backend service that stores lottery tickets submitted by users and schedules them for the next weekly draw.
App contains Unit and Integration tests.
---

## 📝 Opis projektu

**LotteryApp** to modularny monolit napisany w Javie (Spring Boot), który:

- przyjmuje zgłoszenia z 6 unikalnymi liczbami (1–99),
- przypisuje je do losowania w najbliższą sobotę o godzinie 12:00 (jeśli aktualny dzień to sobota < 12:00, losowanie odbędzie się tego
  samego dnia; w przeciwnym razie − w następną sobotę)
- przechowuje dane w MongoDB,
- posiada walidację wejścia,
- zawiera testy jednostkowe i integracyjne.

---

## 🛠️ Stos technologiczny

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

## 🧱 Architektura

- **Modularny monolit** – logiczny podział na moduły ułatwia rozwój i utrzymanie
- **Heksagonalna architektura (Ports and Adapters)** – wyraźny podział na warstwy domeny, aplikacji i infrastruktury


<img width="840" height="632" alt="Lottery - architecture" src="https://github.com/vvhoffmann/LotteryApp/blob/master/architecture/Lottery%20-%20architecture.png" />

* **Rozdzielenie odpowiedzialności** – zasady biznesowe znajdują się w pakiecie `pl.lottery.domain.*`; zależności
  zewnętrzne są wstrzykiwane przez konstruktor fasady, co ułatwia testowanie.
* **Wzorzec „Facade”** – pojedynczy punkt wejścia do funkcjonalności modułu upraszcza integrację z
  innymi modułami lub warstwą REST.
* **Konteneryzacja** – plik `docker-compose.yml` uruchamia jednocześnie MongoDB (z GUI mongo‑express) i obraz zbudowany
  z Dockerfile aplikacji.

---
## 🔌 Endpointy REST API

### 🎟️ Zgłoszenie losu

| Metoda | Endpoint        | Opis                          |
|--------|-----------------|-------------------------------|
| POST   | `/inputNumbers` | Zgłoszenie nowego losu        | 

Request Body (JSON):
```json 
{
"numbers": [5, 12, 23, 34, 45, 67]
}                               
```

📌 Walidacja:
- Dokładnie 6 liczb,
- Każda liczba w zakresie 1–99,
- Wszystkie muszą być unikalne.

Przykładowe Response body:

```json 
{
  "ticketDto": {
    "ticketId": "96a34587-9945-4aba-b13f-0caadfbaab85",
    "drawDate": "2025-08-02T12:00:00",
    "numbers": [1,2,3,4,5,6]
  },
  "message": "SUCCESS"
}
```

---
### 🎟️ Uzyskiwanie wyników

| Metoda | Endpoint               | Opis                                   |
|--------|------------------------|----------------------------------------|
| GET    | `/results/{ticketId}`  | Wyniki najnowszego losowania           |


Przykładowe Body Response (JSON) :

```json
{
  "resultResponseDto": {
    "id": "ce2e02d6-92b9-498a-983d-7cbcdd8970a2",
    "drawDate": "2025-08-02T12:00:00",
    "numbers": [1,2,3,4,5,6],
    "hitNumbers": [1,2,3,4,5],
    "isWinner": true
  },
  "message": "Congratulations! You won"
}
```
