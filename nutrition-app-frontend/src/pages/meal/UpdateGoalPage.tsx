import React, { useEffect, useState } from 'react';
import { Card, message, Spin } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import { apiService } from '../../services/api';
import GoalForm from '../../components/meal/GoalForm';
import type { GoalsStatistics } from '../../types/MealEntities';
import { getRedirectedPath } from '../../utils/url';

const UpdateGoalPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [initialGoals, setInitialGoals] = useState<GoalsStatistics | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchGoals = async () => {
    try {
      const response = await apiService.getGoals();
      if (response.body.status === 'OK' && response.body.payload) {
        setInitialGoals(response.body.payload);
      } else {
        message.error('Failed to load goals');
      }
    } catch (error) {
      console.error('Error fetching goals:', error);
      message.error('An unexpected error occurred');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchGoals();
  }, []);

  const handleSubmit = async (values: GoalsStatistics) => {
    try {
      const response = await apiService.updateGoals(values);
      if (response.body.status === 'OK') {
        message.success('Goals updated successfully');
        navigate(getRedirectedPath(location.search));
      } else {
        const errors = response.body.errors?.map((e: { message: string }) => e.message).join(', ') || 'Failed to update goals';
        message.error(errors);
      }
    } catch (error) {
      console.error('Failed to update goals:', error);
      message.error('An unexpected error occurred');
    }
  };

  const handleCancel = () => {
    navigate(getRedirectedPath(location.search));
  };

  return (
    <Card title="Edit Nutrition Goals" style={{ maxWidth: 1000, margin: '0 auto' }}>
      {loading ? (
        <Spin tip="Loading..." />
      ) : initialGoals ? (
        <GoalForm initialValues={initialGoals} onSubmit={handleSubmit} onCancel={handleCancel} />
      ) : (
        <p>No goals data available</p>
      )}
    </Card>
  );
};

export default UpdateGoalPage;
