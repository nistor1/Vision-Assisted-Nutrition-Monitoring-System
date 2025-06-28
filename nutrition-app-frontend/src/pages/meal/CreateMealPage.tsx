import React from 'react';
import { Card, message } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import type { CreateMealRequest } from '../../types/MealEntities';
import { apiService } from '../../services/api';
import MealForm from '../../components/meal/MealForm';
import { getRedirectedPath } from '../../utils/url';

const CreateMealPage: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const handleSubmit = async (values: CreateMealRequest) => {
        try {
            const response = await apiService.createMeal(values);

            if (response.body.status === 'OK') {
                message.success('Meal created successfully');
                navigate(getRedirectedPath(location.search));
            } else {
                const errors = response.body.errors?.map((e) => e.message).join(', ') || 'Failed to create meal';
                message.error(errors);
            }
        } catch (error) {
            console.error('Failed to create meal:', error);
            message.error('An unexpected error occurred');
        }
    };

    const handleCancel = () => {
        navigate(getRedirectedPath(location.search));
    };

    return (
        <Card title="Create New Meal" style={{ maxWidth: 1000, margin: '0 auto' }}>
            <MealForm onSubmitSuccess={handleSubmit} onCancel={handleCancel} />
        </Card>
    );
};

export default CreateMealPage;
