import React from 'react';
import { Table, Button, Space, Tag } from 'antd';
import type { User } from '../../types/entities';
import dayjs from 'dayjs';

interface UsersTableProps {
  data: User[];
  onView: (id: string) => void;
  onEdit: (id: string) => void;
  onDelete: (id: string) => void;
}

const UsersTable: React.FC<UsersTableProps> = ({ data, onView, onEdit, onDelete }) => {
  const columns = [
    {
      title: 'Username',
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
    },
    {
      title: 'Created At',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (value: string) => dayjs(value).format('YYYY-MM-DD HH:mm'),
    },
    {
      title: 'Role',
      dataIndex: 'role',
      key: 'role',
      render: (role: string) => <Tag color={role === 'ADMIN' ? 'red' : 'blue'}>{role}</Tag>,
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_: unknown, record: User) => (
        <Space>
          <Button size="small" onClick={() => onView(record.id)}>View</Button>
          <Button size="small" type="primary" onClick={() => onEdit(record.id)}>Edit</Button>
          <Button size="small" danger onClick={() => onDelete(record.id)}>Delete</Button>
        </Space>
      ),
    },
  ];

  return (
    <Table
      dataSource={data}
      columns={columns}
      rowKey="id"
      pagination={{
        showSizeChanger: true,
        pageSizeOptions: ['5', '10', '20', '30'],
        defaultPageSize: 5,
      }}
    />
  );
};

export default UsersTable;
