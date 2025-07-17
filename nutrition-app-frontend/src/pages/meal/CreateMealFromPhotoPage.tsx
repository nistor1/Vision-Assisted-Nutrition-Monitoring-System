import React, { useState } from 'react';
import { Card, Button, message } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import { apiService } from '../../services/api';
import { getRedirectedPath } from '../../utils/url';
import ImageUploader from '../../components/meal/LoadPhoto';

const CreateMealFromPhotoPage: React.FC = () => {
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();
  const location = useLocation();
  const redirectPath = getRedirectedPath(location.search);

  const handleImageSelected = (file: File) => {
    setImageFile(file);
  };

  const handleCancel = () => {
    navigate(redirectPath);
  };

  const handleSubmit = async () => {
    if (!imageFile) {
      message.warning('Please select an image first.');
      return;
    }

    const formData = new FormData();
    formData.append('image', imageFile);

    setLoading(true);
    try {
      const response = await apiService.createMealFromPhoto(formData);
      if (response.body.status === 'OK' && response.body.payload) {
        message.success('Meal draft generated from photo successfully');
        const mealDto = response.body.payload;

        navigate(`/meals/new/request?redirect=` + location.pathname, {state: {mealDto}});
      } else {
        const errors =
          response.body.errors?.map(e => e.message).join(', ') ||
          'Failed to create meal from photo';
        message.error(errors);
      }
    } catch (err) {
      console.error('Create from photo failed:', err);
      message.error('Unexpected error occurred');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card title="Create Meal from Photo" style={{ maxWidth: 800, margin: '0 auto' }}>
      <ImageUploader onImageSelected={handleImageSelected} onCancel={handleCancel} />
      <div style={{ marginTop: 24 }}>
        <Button
          type="primary"
          onClick={handleSubmit}
          disabled={!imageFile}
          loading={loading}
        >
          Create Meal from Photo
        </Button>
      </div>
    </Card>
  );
};

export default CreateMealFromPhotoPage;
