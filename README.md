# CafeteriaApp

CafeteriaApp to aplikacja mobilna typu MVP dla kawiarni. Projekt przedstawia program lojalnościowy, w którym użytkownik zbiera pieczątki, odbiera nagrody, przegląda menu kawiarni, korzysta z promocji dnia oraz zapisuje podstawowe dane profilu.

Aplikacja została wykonana jako natywna aplikacja Android w języku Kotlin z wykorzystaniem Jetpack Compose. Projekt zawiera również prosty backend REST API napisany w Node.js i Express.

## Cel projektu

Celem projektu jest przygotowanie działającego prototypu aplikacji lojalnościowej dla kawiarni. Aplikacja realizuje wybrane funkcje w pełni, a część ekranów i opcji ma charakter demonstracyjny zgodny z założeniami MVP.

Główna działająca funkcja aplikacji to obsługa karty lojalnościowej:

- dodawanie pieczątek,
- zapisywanie liczby pieczątek lokalnie,
- odblokowywanie nagród,
- odbieranie nagród,
- wyświetlanie historii odebranych nagród.

## Technologie

- Android native
- Kotlin
- Jetpack Compose
- Material 3
- Android DataStore
- ViewModel
- Node.js
- Express.js
- REST API

## Najważniejsze funkcje

- ekran startowy z logotypem aplikacji,
- ekran karty lojalnościowej z animowanymi pieczątkami,
- lokalny zapis liczby pieczątek, ustawień i profilu użytkownika,
- ekran nagród pobieranych z backendu,
- odbieranie nagród i historia odbiorów,
- ekran menu kawiarni pobierany z API,
- promocja dnia pobierana z API,
- odświeżanie promocji po potrząśnięciu telefonem,
- powiadomienia o dostępnej i odebranej nagrodzie,
- ekran profilu użytkownika,
- ekran ustawień,
- tryb ciemny,
- ekran informacji o autorach z logo IDEIS,
- obsługa układu pionowego i poziomego,
- zapamiętywanie stanu przy obrocie ekranu.

## Zgodność z wymaganiami

| Wymaganie | Realizacja w projekcie |
| --- | --- |
| Splash screen z logiem aplikacji | Ekran startowy `SplashScreen` |
| Ekran konfiguracyjny | Ekran `Ustawienia` |
| Informacje o autorach i logo IDEIS | Ekran `Autorzy` |
| Układ pionowy i poziomy | Osobne układy w ekranach aplikacji |
| Zapamiętywanie stanu przy obrocie | `rememberSaveable`, ViewModel i DataStore |
| Lokalny zapis danych | Android DataStore |
| Komunikacja sieciowa | REST API backendu |
| Elementy nawigacyjne | Bottom navigation |
| Powiadomienia | Powiadomienia o nagrodach |
| Czujniki | Akcelerometr, odświeżanie promocji po potrząśnięciu |
| Wzorzec architektoniczny | MVVM po stronie aplikacji Android |
| Animacje | Animowany pasek postępu i pieczątki |
| Backend | Node.js + Express + endpointy API |

## Backend API

Backend znajduje się w katalogu `backend`.

Struktura backendu:

```text
backend/
  server.js
  data/
    menu.js
    rewards.js
    promotion.js
  routes/
    menuRoutes.js
    rewardsRoutes.js
    promotionRoutes.js
    claimsRoutes.js
  services/
    claimsStore.js
```

Endpointy:

| Metoda | Endpoint | Opis |
| --- | --- | --- |
| GET | `/api/menu` | Lista produktów w menu kawiarni |
| GET | `/api/rewards` | Lista nagród programu lojalnościowego |
| GET | `/api/promotion` | Promocja dnia |
| GET | `/api/claims` | Historia odebranych nagród |
| POST | `/api/claims` | Dodanie odebranej nagrody do historii |
| DELETE | `/api/claims` | Wyczyszczenie historii odebranych nagród |

Backend działa lokalnie pod adresem:

```text
http://localhost:3000
```

Aplikacja Android na emulatorze łączy się z backendem przez adres:

```text
http://10.0.2.2:3000
```

## Uruchomienie backendu

W katalogu projektu należy przejść do folderu `backend`:

```powershell
cd backend
```

Zainstalować zależności:

```powershell
npm install
```

Uruchomić serwer:

```powershell
npm start
```

Po uruchomieniu można sprawdzić API w przeglądarce:

```text
http://localhost:3000/api/menu
http://localhost:3000/api/rewards
http://localhost:3000/api/promotion
http://localhost:3000/api/claims
```

## Uruchomienie aplikacji Android

1. Otworzyć projekt w Android Studio.
2. Uruchomić backend komendą `npm start` w katalogu `backend`.
3. Wybrać emulator lub urządzenie Android.
4. Uruchomić aplikację przyciskiem Run.

Jeżeli backend nie jest uruchomiony, aplikacja nadal wyświetla dane domyślne dla menu, promocji i nagród. Historia odbiorów z API nie będzie wtedy dostępna.

## Lokalny zapis danych

Aplikacja wykorzystuje Android DataStore do zapisywania danych lokalnych na urządzeniu użytkownika. Zapisywane są:

- liczba pieczątek,
- ustawienie powiadomień,
- ustawienie trybu ciemnego,
- imię klienta w profilu.

Dane zapisane lokalnie zostają na danym emulatorze lub urządzeniu po ponownym uruchomieniu aplikacji.

## Architektura

Projekt po stronie Androida wykorzystuje podejście MVVM:

- ekrany Jetpack Compose odpowiadają za widok,
- `CafeteriaViewModel` przechowuje stan aplikacji i obsługuje logikę,
- klasy API pobierają dane z backendu,
- `AppPreferencesRepository` odpowiada za lokalny zapis danych w DataStore.

Backend jest podzielony na:

- `data` - przykładowe dane aplikacji,
- `routes` - obsługa endpointów API,
- `services` - prosta logika przechowywania historii odebranych nagród,
- `server.js` - konfiguracja i uruchomienie serwera Express.

## Uwagi

Projekt ma charakter MVP. Część funkcji jest uproszczona lub demonstracyjna, ale aplikacja zawiera pełny przepływ zbierania pieczątek, odbierania nagród, zapisu lokalnego oraz komunikacji z backendem.

Historia odebranych nagród w backendzie jest przechowywana w pamięci serwera, dlatego po restarcie backendu zostaje wyczyszczona. Lokalny stan aplikacji Android jest zapisywany oddzielnie w DataStore.

## Autorzy

- Hubert Rola
- Łukasz Janus

