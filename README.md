# CafeteriaApp

CafeteriaApp to natywna aplikacja Android napisana w Kotlinie z użyciem Jetpack Compose. Projekt jest aplikacją typu MVP dla programu lojalnościowego kawiarni. Główna działająca funkcja to karta pieczątek, odbieranie nagród oraz zapisywanie postępu użytkownika lokalnie.

Aplikacja realizuje temat: **Aplikacja lojalnościowa - kawiarnia**.

## Cel Projektu

Celem projektu jest pokazanie prototypu aplikacji lojalnościowej dla kawiarni. Aplikacja zawiera komplet głównych ekranów, wygląda jak docelowa aplikacja mobilna i realizuje jedną główną funkcję w pełnym przepływie:

- zbieranie pieczątek,
- blokada dodawania kolejnych pieczątek po wypełnieniu karty,
- odbieranie nagród,
- zapisywanie pieczątek i historii odbiorów lokalnie,
- wyświetlanie menu, promocji, profilu, ustawień i lokalizacji kawiarni.

Część funkcji ma charakter demonstracyjny, zgodnie z założeniami MVP.

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

## Najważniejsze Funkcje

- splash screen z logiem aplikacji,
- karta lojalnościowa z animowanymi pieczątkami,
- blokada dodawania pieczątek po osiągnięciu 8/8,
- odbieranie nagród z poziomu ekranu nagród,
- lokalny zapis liczby pieczątek,
- lokalny zapis historii odebranych nagród,
- lokalny zapis ustawień i nazwy klienta,
- menu kawiarni pobierane z backendu,
- lista nagród pobierana z backendu,
- promocja dnia pobierana z backendu,
- awaryjne dane domyślne, gdy backend jest niedostępny,
- powiadomienia o dostępnej i odebranej nagrodzie,
- odświeżanie promocji po potrząśnięciu telefonem,
- ekran profilu klienta,
- ekran konfiguracji,
- tryb ciemny,
- ekran autorów z logo IDEIS,
- ekran kawiarni z lokalem demonstracyjnym na Rynku Głównym w Krakowie,
- przycisk otwierający lokalizację kawiarni w aplikacji Mapy,
- obsługa układu pionowego i poziomego,
- zapamiętywanie stanu przy obrocie ekranu.

## Ekrany Aplikacji

- **Splash screen** - ekran startowy z logiem aplikacji.
- **Karta** - karta lojalnościowa z pieczątkami i postępem.
- **Nagrody** - lista nagród, odbieranie nagród i historia odbiorów.
- **Menu** - menu kawiarni oraz promocja dnia.
- **Kawiarnie** - lokal demonstracyjny, adres, współrzędne i planowane punkty.
- **Profil** - dane klienta, poziom lojalnościowy i statystyki.
- **Ustawienia** - powiadomienia, tryb ciemny i przejście do informacji o autorach.
- **Autorzy** - autorzy projektu oraz logo IDEIS.

## Zgodność Z Wymaganiami

| Wymaganie | Realizacja w projekcie |
| --- | --- |
| Splash screen z logiem aplikacji | Ekran `SplashScreen` |
| Ekran konfiguracyjny | Ekran `Ustawienia` |
| Informacje o autorach i logo IDEIS | Ekran `Autorzy` |
| Układ pionowy i poziomy | Osobne układy w ekranach przez `LocalConfiguration` |
| Zapamiętywanie stanu przy obrocie | `rememberSaveable`, ViewModel i DataStore |
| Lokalny zapis danych | Android DataStore |
| Komunikacja sieciowa | Backend REST API w Node.js/Express |
| Elementy nawigacyjne | Bottom navigation |
| Powiadomienia | Powiadomienia o nagrodach |
| Czujniki | Akcelerometr, odświeżanie promocji po potrząśnięciu |
| Mapy / lokalizacja demonstracyjna | Ekran `Kawiarnie` i otwieranie punktu w aplikacji Mapy |
| Wzorzec architektoniczny | MVVM po stronie Androida |
| Animacje | Animowany pasek postępu i pieczątki |

## Lokalny Zapis Danych

Aplikacja wykorzystuje Android DataStore. Lokalnie zapisywane są:

- liczba pieczątek,
- ustawienie powiadomień,
- ustawienie trybu ciemnego,
- nazwa klienta w profilu,
- historia odebranych nagród.

Historia nagród jest zapisywana lokalnie niezależnie od backendu. Jeśli backend działa, aplikacja scala historię z API z historią lokalną. Jeśli backend jest niedostępny, nagroda nadal zostaje odebrana i zapisana lokalnie.

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

## Uruchomienie Backendu

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

## Uruchomienie Aplikacji Android

1. Otworzyć projekt w Android Studio.
2. Uruchomić backend komendą `npm start` w katalogu `backend`.
3. Wybrać emulator lub urządzenie Android.
4. Uruchomić aplikację przyciskiem Run.

Jeżeli backend nie jest uruchomiony, aplikacja nadal wyświetla dane domyślne dla menu, promocji i nagród. Historia odebranych nagród nadal działa lokalnie dzięki DataStore.

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

Projekt ma charakter MVP. Główna funkcja, czyli karta lojalnościowa, odbieranie nagród oraz lokalny zapis postępu, działa w pełnym przepływie. Pozostałe elementy, takie jak planowane punkty kawiarni i część opcji konfiguracyjnych, mają charakter demonstracyjny.

Historia odebranych nagród w backendzie jest przechowywana w pamięci serwera, dlatego po restarcie backendu zostaje wyczyszczona. Lokalna historia w aplikacji Android jest zapisywana w DataStore i zostaje na urządzeniu.

## Autorzy

- Hubert Rola
- Łukasz Janus
