import React, { useEffect, useState } from 'react';
import {
  BarChart, Bar,
  XAxis, YAxis,
  CartesianGrid, Tooltip,
  Legend, ResponsiveContainer
} from 'recharts';
import axiosInstance from "../../api/axiosInstance.js";
const token = localStorage.getItem("token");
const user_id = localStorage.getItem("user_id");

function MainGraph() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axiosInstance.get(
            `/users/main/${user_id}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
        );

        const tilList = response.data.tilList;

        // subjectName 별로 개수 세기
        const countMap = tilList.reduce((acc, cur) => {
          acc[cur.subjectName] = (acc[cur.subjectName] || 0) + 1;
          return acc;
        }, {});

        // 그래프 데이터로 변환
        const graphData = Object.keys(countMap).map(key => ({
          name: key,
          리스트: countMap[key]
        }));

        setData(graphData);

      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  return (
      <ResponsiveContainer width="100%" height={400}>
        <BarChart
            width={500}
            height={300}
            data={data}
            margin={{
              top: 20, right: 30, left: -10, bottom: 5,
            }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis domain={[0, 100]} />
          <Tooltip />
          <Legend />
          <Bar dataKey="리스트" fill="#8884d8" />
        </BarChart>
      </ResponsiveContainer>
  );
}

export default MainGraph;
