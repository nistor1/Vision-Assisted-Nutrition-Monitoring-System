import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import FoodItemView from '../../components/food/FoodItemView.tsx';

const FoodItemViewPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  if (!id) return null;

  return <FoodItemView foodItemId={id} onClose={() => navigate(-1)} />;
};

export default FoodItemViewPage;
