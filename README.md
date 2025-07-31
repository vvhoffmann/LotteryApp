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

```
<img width="840" height="632" alt="Lottery - architecture" src="https://github.com/user-attachments/assets/9c171cac-2381-4b72-92cc-2aeca0e56e21" />

* **Rozdzielenie odpowiedzialności** – zasady biznesowe znajdują się w pakiecie `pl.lottery.domain.*`; zależności
  zewnętrzne są wstrzykiwane przez konstruktor fasady, co ułatwia testowanie.
* **Wzorzec „Facade”** – pojedynczy punkt wejścia do funkcjonalności modułu upraszcza integrację z
  innymi modułami lub warstwą REST.
* **Konteneryzacja** – plik `docker-compose.yml` uruchamia jednocześnie MongoDB (z GUI mongo‑express) i obraz zbudowany
  z Dockerfile aplikacji.

---
