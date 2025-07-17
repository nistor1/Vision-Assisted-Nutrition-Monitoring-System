import React, { useEffect, useState } from 'react';
import { Button, Select, Space, Typography, message, Spin } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import { apiService } from '../../services/api';
import FoodTable from '../../components/food/FoodTable';
import type { FoodItemSimple } from '../../types/FoodEntities';
import type { TablePaginationConfig } from 'antd/es/table';
import { useAuth } from '../../context/AuthContext';

const { Title } = Typography;
const { Option } = Select;

const FoodPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [foods, setFoods] = useState<FoodItemSimple[]>([]);
  const [categoryFilter, setCategoryFilter] = useState<string | undefined>();
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 5, total: 0 });
  const { user } = useAuth();
  const isAdmin = user.role === 'ADMIN';

  const fetchFoodItems = async (page = 0, size = 5, category?: string) => {
    setLoading(true);
    try {
      console.log('Fetching food items with params:', { page, size, category });
      const response = await apiService.getFoodItemsSimple({ page, size, category });
      const payload = response.body.payload;
      console.log('Fetched food items: ', payload);
      setFoods(payload?.content || []);
      setPagination({
        current: page + 1,
        pageSize: size,
        total: payload?.totalElements || 0,
      });
      console.log('Pagination state updated:', pagination);
    } catch (err) {
      message.error('Failed to fetch food items');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchFoodItems(pagination.current - 1, pagination.pageSize, categoryFilter);
  }, [pagination.current, pagination.pageSize, categoryFilter]);

  const handleAdd = () => navigate('/admin/food-items/new?redirect=' + location.pathname);
  const handleView = (id: string) => navigate(`/food-items/${id}`);
  const handleEdit = (id: string) => navigate(`/admin/food-items/edit/${id}?redirect=` + location.pathname);
  const handleDelete = async (id: string) => {
    try {
      const response = await apiService.deleteFoodItem(id);
      if(response.status !== 'OK') {
        message.error('Delete failed');
        return;
      }
      message.success('Food item deleted');
      fetchFoodItems(pagination.current - 1, pagination.pageSize, categoryFilter);
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
      {isAdmin && (
        <Space style={{ marginBottom: 24, width: '100%', justifyContent: 'space-between' }}>
          <Title level={2}>Food Items</Title>
          <Button type="primary" onClick={handleAdd}>Add Food</Button>
        </Space>
      )}

      <Space style={{ marginBottom: 16 }}>
        <span>Filter by Category:</span>
        <Select
          allowClear
          placeholder="Select category"
          value={categoryFilter}
          onChange={(value) => setCategoryFilter(value)}
          style={{ width: 200 }}
        >
          {['Fruit', 'Vegetable', 'Other'].map((cat) => (
            <Option key={cat} value={cat}>{cat}</Option>
          ))}
        </Select>

      </Space>

      {loading ? (
        <Spin />
      ) : (
        <FoodTable
          data={foods}
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

export default FoodPage;
