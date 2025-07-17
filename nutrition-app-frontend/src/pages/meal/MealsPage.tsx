import React, { useEffect, useState } from 'react';
import {
  Button,
  Space,
  Typography,
  message,
  Spin,
  DatePicker,
} from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import type { TablePaginationConfig } from 'antd/es/table';
import type { Meal } from '../../types/MealEntities';
import { apiService } from '../../services/api';
import MealTable from '../../components/meal/MealTable';
import { Dayjs } from 'dayjs';

const { Title } = Typography;

const MealsPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [meals, setMeals] = useState<Meal[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 5, total: 0 });
  const [selectedDate, setSelectedDate] = useState<Dayjs | null>(null);

  const fetchMeals = async (page = 0, size = 5, date?: string) => {
    setLoading(true);
    try {
      const response = await apiService.getMealsForUser({page, size, date});
      const payload = response.body.payload;
      setMeals(payload?.content || []);
      setPagination({
        current: page + 1,
       pageSize: size,
        total: payload?.totalElements || 0,
      });
    } catch (err) {
      message.error('Failed to fetch meals');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const isoDate = selectedDate?.format('YYYY-MM-DD') ?? undefined;
    fetchMeals(pagination.current - 1, pagination.pageSize, isoDate);
  }, [pagination.current, pagination.pageSize, selectedDate]);

  const handleAddFromForm = () => navigate('/meals/new/request?redirect=' + location.pathname);
  const handleAdd = () => navigate('/meals/new/?redirect=' + location.pathname);
  const handleView = (id: string) => navigate(`/meals/${id}`);
  const handleEdit = (id: string) => navigate(`/meals/edit/${id}?redirect=` + location.pathname);
  const handleDelete = async (id: string) => {
    try {
      const response = await apiService.deleteMeal(id);
      if(response.status !== 'OK') {
        message.error('Delete failed');
        return;
      }
      message.success('Meal deleted');
      const isoDate = selectedDate?.format('YYYY-MM-DD') ?? undefined;
      fetchMeals(pagination.current - 1, pagination.pageSize, isoDate);
    } catch {
      message.error('Delete failed');
    }
  };

  const handleTableChange = (paginationData: TablePaginationConfig) => {
    const current = paginationData.current || 1;
    const pageSize = paginationData.pageSize || 5;

    setPagination((prev) => ({
      ...prev,
      current,
      pageSize,
    }));
  };

  return (
    <div style={{ padding: 24 }}>
      <Space
        direction="vertical"
        size="middle"
        style={{ marginBottom: 24, width: '100%' }}
      >
        <Space style={{ justifyContent: 'space-between', width: '100%' }}>
          <Title level={2}>Meals</Title>
          <div>
            <Button type="primary" onClick={handleAdd}  style={{ marginRight: '8px' }}>Add Meal</Button>
            <Button onClick={handleAddFromForm}>Add Meal Form</Button>
          </div>
        </Space>

        <Space>
          <span>Filter by date:</span>
          <DatePicker
            allowClear
            value={selectedDate}
            onChange={(date) => {
              setPagination((prev) => ({ ...prev, current: 1 }));
              setSelectedDate(date);
            }}
          />
        </Space>
      </Space>

      {loading ? (
        <Spin />
      ) : (
        <MealTable
          data={meals}
          onView={handleView}
          onEdit={handleEdit}
          onDelete={handleDelete}
          pagination={pagination}
          onChange={handleTableChange}
        />
      )}
    </div>
  );
};

export default MealsPage;
