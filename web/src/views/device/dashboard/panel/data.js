import { getTotal, getTDS, getHistory, getColor, getTur } from './api';
export const type2Api = [
  { id: 1, type: '设备总览', fetchData: getTotal, component: 'total' },
  { id: 2, type: 'TDS近7日趋势图', fetchData: getTDS, component: 'trend' },
  { id: 3, type: '最近15日用水量图', fetchData: getHistory, component: 'history' },
  { id: 4, type: '色度近7日趋势图', fetchData: getColor, component: 'trend' },
  { id: 5, type: '浊度近7日趋势图', fetchData: getTur, component: 'trend' },
]