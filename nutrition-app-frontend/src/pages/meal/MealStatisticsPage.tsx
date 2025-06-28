import React, { useEffect, useState } from 'react';
import { Card, Typography, DatePicker, Radio, Spin, Alert, Button,} from 'antd';
import dayjs, { Dayjs } from 'dayjs';
import { apiService } from '../../services/api';
import type { DailyStatisticResponse, GoalsStatistics } from '../../types/MealEntities';
import DailyStatisticView from '../../components/meal/MealDayStatistics';
import { useNavigate, useLocation } from 'react-router-dom';

const { Title } = Typography;

type ViewMode = 'day' | 'week';

const flattenGoals = (goals: GoalsStatistics): Record<string, number> => {
  const flat: Record<string, number> = {};

  for (const [key, value] of Object.entries(goals)) {
    if (typeof value === 'number') {
      flat[key] = value;
    } else if (value === null) {
      flat[key] = 0;
    }
  }

  return flat;
};

const MealStatisticsPage: React.FC = () => {
  const [mode, setMode] = useState<ViewMode>('week');
  const [selectedDate, setSelectedDate] = useState<Dayjs>(dayjs());
  const [stats, setStats] = useState<DailyStatisticResponse[]>([]);
  const [goals, setGoals] = useState<Record<string, number>>({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const location = useLocation();

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [statRes, goalRes] = await Promise.all([
        mode === 'week'
          ? apiService.getWeeklyStatistics()
          : apiService.getDailyStatistics(selectedDate.format('YYYY-MM-DD')),
        apiService.getGoals(),
      ]);

      const statPayload = statRes.body.payload;
      const goalPayload = goalRes.body.payload;

      if (goalPayload) {
        setGoals(flattenGoals(goalPayload));
      }

      if (Array.isArray(statPayload)) setStats(statPayload);
      else if (statPayload) setStats([statPayload]);
      else setStats([]);
    } catch {
      setError('Failed to load statistics');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [mode, selectedDate]);

  return (
    <Card style={{maxWidth: 1200, margin: '0 auto'}}>
      <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16}}>
        <Title level={3} style={{margin: 0}}>Meal Nutrition Statistics</Title>
        <div>
          <Button type="primary" onClick={() => navigate(`/goals/edit?redirect=` + location.pathname)}
                  style={{marginRight: 8}}>
            Edit Goals
          </Button>
        </div>
      </div>
      <Radio.Group
        value={mode}
        onChange={(e) => setMode(e.target.value)}
        style={{marginBottom: 16}}
      >
        <Radio.Button value="week">Last 7 Days</Radio.Button>
        <Radio.Button value="day">Specific Day</Radio.Button>
      </Radio.Group>

      {mode === 'day' && (
        <DatePicker
          value={selectedDate}
          onChange={(d) => d && setSelectedDate(d)}
          style={{marginBottom: 16}}
        />
      )}

      {loading ? (
        <Spin tip="Loading statistics..."/>
      ) : error ? (
        <Alert message={error} type="error" showIcon/>
      ) : stats.length === 0 || Object.keys(goals).length === 0 ? (
        <Alert message="No statistics available." type="info" showIcon/>
      ) : mode === 'day' ? (
        <DailyStatisticView stat={stats[0]} goals={goals}/>
      ) : (
        <>
          {stats.map((s) => (
            <Card key={s.date} title={`Date: ${s.date}`} style={{marginBottom: 24}}>
              <DailyStatisticView stat={s} goals={goals}/>
            </Card>
          ))}
        </>
      )}
    </Card>
  );
};
export default MealStatisticsPage;