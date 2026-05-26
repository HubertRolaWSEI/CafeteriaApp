const express = require("express");
const cors = require("cors");

const app = express();
const port = 3000;

app.use(cors());
app.use(express.json());

const menu = [
  {
    name: "Espresso",
    description: "Klasyczna, intensywna kawa z wyrazista crema",
    price: "8 zl"
  },
  {
    name: "Americano",
    description: "Espresso przedluzone goraca woda",
    price: "10 zl"
  },
  {
    name: "Cappuccino",
    description: "Espresso, spienione mleko i delikatna pianka",
    price: "13 zl"
  },
  {
    name: "Latte",
    description: "Lagodna kawa mleczna z pojedynczym espresso",
    price: "15 zl"
  },
  {
    name: "Flat White",
    description: "Podwojne espresso z aksamitnym mlekiem",
    price: "16 zl"
  },
  {
    name: "Mocha",
    description: "Kawa mleczna z czekolada i bita smietana",
    price: "17 zl"
  },
  {
    name: "Cold Brew",
    description: "Kawa parzona na zimno przez kilkanascie godzin",
    price: "16 zl"
  },
  {
    name: "Matcha Latte",
    description: "Matcha z mlekiem, delikatna i kremowa",
    price: "18 zl"
  },
  {
    name: "Croissant maslany",
    description: "Chrupacy croissant wypiekany na miejscu",
    price: "11 zl"
  },
  {
    name: "Sernik",
    description: "Domowy sernik z wanilia i kruchym spodem",
    price: "14 zl"
  },
  {
    name: "Brownie",
    description: "Czekoladowe brownie z orzechami",
    price: "13 zl"
  },
  {
    name: "Kanapka klubowa",
    description: "Pieczywo pszenne, kurczak, salata, pomidor i sos",
    price: "19 zl"
  }
];

const rewards = [
  {
    id: 1,
    title: "Darmowa kawa",
    description: "Nagroda po zebraniu 8 pieczatek na karcie lojalnosciowej.",
    requiredStamps: 8,
    status: "Aktywna"
  },
  {
    id: 2,
    title: "Croissant -20%",
    description: "Rabat na croissanta dla stalych klientow kawiarni.",
    requiredStamps: 4,
    status: "Promocja sezonowa"
  },
  {
    id: 3,
    title: "Podwojne punkty",
    description: "Dodatkowa pieczatka przy zakupie kawy w poniedzialki.",
    requiredStamps: 0,
    status: "Wkrotce"
  },
  {
    id: 4,
    title: "Zestaw sniadaniowy -10%",
    description: "Rabat na zestaw kawa plus wypiek przy porannym zakupie.",
    requiredStamps: 6,
    status: "Wkrotce"
  }
];

const promotion = {
  title: "Promocja dnia",
  description: "Do kazdej kawy Latte croissant -20%",
  validToday: true,
  code: "LATTE20"
};

app.get("/", (req, res) => {
  res.json({
    name: "Cafeteria backend",
    endpoints: ["/api/menu", "/api/rewards", "/api/promotion"]
  });
});

app.get("/api/menu", (req, res) => {
  res.json(menu);
});

app.get("/api/rewards", (req, res) => {
  res.json(rewards);
});

app.get("/api/promotion", (req, res) => {
  res.json(promotion);
});

app.listen(port, () => {
  console.log(`Cafeteria backend running on http://localhost:${port}`);
});
