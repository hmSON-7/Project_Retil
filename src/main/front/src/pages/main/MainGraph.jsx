import React from 'react';
import {
  BarChart, Bar,
  XAxis, YAxis,
  CartesianGrid, Tooltip,
  Legend, ResponsiveContainer
} from 'recharts';

// 데이터 배열 정의
const data = [
  {
    name: "국어",
    리스트: 85
  },
  {
    name: "수학",
    리스트: 3
  },
  {
    name: "사회",
    리스트: 1
  },
  {
    name: "과학",
    리스트: 2
  },
  {
    name: "영어",
    리스트: 4
  },
  {
    name: "체육",
    리스트: 2
  },
  {
    name: "음악",
    리스트: 3
  },
  {
    name: "미술",
    리스트: 5
  }
];

// 함수형 컴포넌트 정의
function MainGraph() {
  return (
    // 반응형 컨테이너로 차트를 감싸서 반응형 디자인 지원
    <ResponsiveContainer width="100%" height={400}>
      <BarChart
        width={500}
        height={300}
        data={data}
        margin={{
          top: 20, right: 30, left: -10, bottom: 5,
        }}
      >
        {/* 그리드 라인 추가 */}
        <CartesianGrid strokeDasharray="3 3" />
        {/* X축 설정, 데이터의 "name" 키 사용 */}
        <XAxis dataKey="name" />
        {/* Y축 설정, 범위 0에서 100으로 설정 */}
        <YAxis domain={[0, 100]} />
        {/* 툴팁 추가, 마우스를 올렸을 때 데이터 표시 */}
        <Tooltip />
        {/* 범례 추가 */}
        <Legend />
        {/* 막대 그래프 설정, 데이터의 "num" 키 사용, 막대 색상 설정 */}
        <Bar dataKey="리스트" fill="#8884d8" />
      </BarChart>
    </ResponsiveContainer>
  );
}


export default MainGraph;

