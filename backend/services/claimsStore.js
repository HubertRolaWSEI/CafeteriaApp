const claims = [];
let nextClaimId = 1;

function getClaims() {
  return claims;
}

function createClaim({ rewardId, rewardTitle, usedStamps }) {
  const claim = {
    id: nextClaimId++,
    rewardId,
    rewardTitle,
    usedStamps,
    claimedAt: new Date().toISOString()
  };

  claims.unshift(claim);
  return claim;
}

function clearClaims() {
  claims.length = 0;
}

module.exports = {
  getClaims,
  createClaim,
  clearClaims
};
