const rewards = [
  {
    id: 1,
    title: "Darmowa kawa",
    description: "Nagroda po zebraniu 8 pieczątek na karcie lojalnościowej.",
    requiredStamps: 8,
    status: "Aktywna"
  },
  {
    id: 2,
    title: "Croissant -20%",
    description: "Rabat na croissanta dla stałych klientów kawiarni.",
    requiredStamps: 4,
    status: "Promocja sezonowa"
  },
  {
    id: 3,
    title: "Podwójne punkty",
    description: "Dodatkowa pieczątka przy zakupie kawy w poniedziałki.",
    requiredStamps: 0,
    status: "Wkrótce"
  },
  {
    id: 4,
    title: "Zestaw śniadaniowy -10%",
    description: "Rabat na zestaw kawa plus wypiek przy porannym zakupie.",
    requiredStamps: 6,
    status: "Wkrótce"
  }
];

module.exports = rewards;
