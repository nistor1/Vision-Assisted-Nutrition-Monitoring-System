import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import MealView from '../../components/meal/MealView.tsx';

const MealViewPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  if (!id) return null;

  return <MealView mealId={id} onClose={() => navigate(-1)} />;
};

export default MealViewPage;
