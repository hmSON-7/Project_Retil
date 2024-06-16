import bronze from "../src/assets/bronze.jpg";
import silver from "../src/assets/silver.jpg";
import gold from "../src/assets/gold.jpg";
import platinum from "../src/assets/platinum.jpg";
import diamond from "../src/assets/diamond.jpg";
import unRanked from "../src/assets/unRanked.jpg"
export function getTier(tierId) {
  switch (tierId) {
    case 1:
      return unRanked;
    case 2:
      return bronze;
    case 3:
      return silver;
    case 4:
      return gold;
    case 5:
      return platinum;
    case 6:
      return diamond;
    default:
      return null;
  }
}
