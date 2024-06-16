export const rankConverter = (rank) => {
    switch (rank) {
        case 'unRanked':
            return 1;
        case 'Bronze':
            return 2;
        case 'Silver':
            return 3;
        case 'Gold':
            return 4;
        case 'Platinum':
            return 5;
        case 'Diamond':
            return 6;
        default:
            return 1; // 기본 값으로 unRanked를 설정
    }
};
