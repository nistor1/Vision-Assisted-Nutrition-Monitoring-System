import React, { useEffect, useState } from 'react';
import { Button, Select, Space, Typography, message, Spin } from 'antd';
import { useNavigate } from 'react-router-dom';
import UsersTable from '../components/user/UsersTable';
import { apiService } from '../services/api';
import type { User } from '../types/entities';

const { Title } = Typography;
const { Option } = Select;

const UsersPage: React.FC = () => {
  const navigate = useNavigate();
  const [users, setUsers] = useState<User[]>([]);
  const [roleFilter, setRoleFilter] = useState<string | undefined>();
  const [loading, setLoading] = useState(false);

  const fetchUsers = async () => {
    setLoading(true);
    try {
      const response = await apiService.getUsers({ page: 0, size: 20 });
      setUsers(response.body.payload?.content || []);
    } catch (err) {
      message.error('Failed to fetch users');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const filteredUsers = roleFilter
    ? users.filter((u) => u.role === roleFilter)
    : users;

  const handleAddUser = () => navigate('/users/new?redirect=' + location.pathname);
  const handleView = (id: string) => navigate(`/users/${id}`);
  const handleEdit = (id: string) => navigate(`/users/edit/${id}?redirect=` + location.pathname);
  const handleDelete = async (id: string) => {
    try {
      await apiService.deleteUser(id);
      message.success('User deleted');
      fetchUsers();
    } catch {
      message.error('Delete failed');
    }
  };

  return (
    <div style={{ padding: 24 }}>
      <Space style={{ marginBottom: 24, width: '100%', justifyContent: 'space-between' }}>
        <Title level={2}>Users</Title>
        <Button
          type="primary" onClick={handleAddUser}>Add User
        </Button>
      </Space>

      <Space style={{ marginBottom: 16 }}>
        <span>Filter by Role:</span>
        <Select
          allowClear
          placeholder="Select role"
          value={roleFilter}
          onChange={(value) => setRoleFilter(value)}
          style={{ width: 200 }}
        >
          <Option value="USER">USER</Option>
          <Option value="ADMIN">ADMIN</Option>
        </Select>
      </Space>

      {loading ? (
        <Spin />
      ) : (
        <UsersTable
          data={filteredUsers}
          onView={handleView}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      )}
    </div>
  );
};

export default UsersPage;
