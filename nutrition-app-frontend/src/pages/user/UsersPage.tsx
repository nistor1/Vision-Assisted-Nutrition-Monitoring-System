import React, { useEffect, useState } from 'react';
import { Button, Select, Space, Typography, message, Spin } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import UsersTable from '../../components/user/UsersTable.tsx';
import { apiService } from '../../services/api.ts';
import type { User } from '../../types/UserEntities.ts';
import type { TablePaginationConfig } from 'antd/es/table';

const { Title } = Typography;
const { Option } = Select;

const UsersPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [users, setUsers] = useState<User[]>([]);
  const [roleFilter, setRoleFilter] = useState<string | undefined>();
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 5, total: 0 });

  const fetchUsers = async (page = 0, size = 5, role?: string) => {
    setLoading(true);
    try {
      const response = await apiService.getUsers({ page, size, role });
      const payload = response.body.payload;
      setUsers(payload?.content || []);
      setPagination({
        current: page + 1,
        pageSize: size,
        total: payload?.totalElements || 0,
      });
    } catch (err) {
      message.error('Failed to fetch users');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers(pagination.current - 1, pagination.pageSize, roleFilter);
  }, [pagination.current, pagination.pageSize, roleFilter]);

  const handleAddUser = () => navigate('/admin/users/new?redirect=' + location.pathname);
  const handleView = (id: string) => navigate(`/admin/users/${id}`);
  const handleEdit = (id: string) => navigate(`/admin/users/edit/${id}?redirect=` + location.pathname);
  const handleDelete = async (id: string) => {
    try {
      const response = await apiService.deleteUser(id);
      if(response.status !== 'OK') {
        message.error('Delete failed');
        return;
      }

      message.success('User deleted');
      fetchUsers(pagination.current - 1, pagination.pageSize, roleFilter);
    } catch {
      message.error('Delete failed');
    }
  };

  const handleTableChange = (paginationData: TablePaginationConfig) => {
    setPagination({
      ...pagination,
      current: paginationData.current || 1,
      pageSize: paginationData.pageSize || 5,
    });
  };

  return (
    <div style={{ padding: 24 }}>
      <Space style={{ marginBottom: 24, width: '100%', justifyContent: 'space-between' }}>
        <Title level={2}>Users</Title>
        <Button type="primary" onClick={handleAddUser}>Add User</Button>
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
          data={users}
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

export default UsersPage;
