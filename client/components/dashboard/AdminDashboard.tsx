"use client"

import { useState, useEffect } from "react"
import {
  Card,
  CardBody,
  CardHeader,
  Button,
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Chip,
  Progress,
} from "@nextui-org/react"
import { Users, DollarSign, TrendingUp, AlertTriangle, Download, Eye } from "lucide-react"
import { useAuth } from "@/contexts/AuthContext"

interface AdminStats {
  totalUsers: number
  totalErrandBoys: number
  totalTasks: number
  totalRevenue: number
  activeUsers: number
  completionRate: number
}

export default function AdminDashboard() {
  const { user } = useAuth()
  const [stats, setStats] = useState<AdminStats>({
    totalUsers: 0,
    totalErrandBoys: 0,
    totalTasks: 0,
    totalRevenue: 0,
    activeUsers: 0,
    completionRate: 0,
  })

  const [recentTasks, setRecentTasks] = useState([])

  useEffect(() => {
    // Mock data - replace with actual API calls
    setStats({
      totalUsers: 1250,
      totalErrandBoys: 340,
      totalTasks: 2890,
      totalRevenue: 45600,
      activeUsers: 890,
      completionRate: 94.5,
    })

    setRecentTasks([
      {
        id: "1",
        title: "Grocery Shopping",
        user: "Sarah M.",
        errandBoy: "John D.",
        status: "COMPLETED",
        amount: 45,
      },
      {
        id: "2",
        title: "House Cleaning",
        user: "Mike R.",
        errandBoy: "Lisa K.",
        status: "IN_PROGRESS",
        amount: 120,
      },
    ])
  }, [])

  const handleExportReport = async () => {
    try {
      const startDate = new Date(Date.now() - 30 * 24 * 60 * 60 * 1000).toISOString()
      const endDate = new Date().toISOString()

      const response = await fetch(
        `https://errand-go.railway.internal/admin/reports/export/excel?start=${startDate}&end=${endDate}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        },
      )

      if (response.ok) {
        const blob = await response.blob()
        const url = window.URL.createObjectURL(blob)
        const a = document.createElement("a")
        a.href = url
        a.download = "admin-report.xlsx"
        document.body.appendChild(a)
        a.click()
        window.URL.revokeObjectURL(url)
        document.body.removeChild(a)
      }
    } catch (error) {
      console.error("Export failed:", error)
    }
  }

  const getStatusColor = (status: string) => {
    switch (status) {
      case "COMPLETED":
        return "success"
      case "IN_PROGRESS":
        return "primary"
      case "PENDING":
        return "warning"
      default:
        return "default"
    }
  }

  return (
    <div className="max-w-7xl mx-auto p-6">
      {/* Header */}
      <div className="mb-8 flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-900 mb-2">Admin Dashboard</h1>
          <p className="text-gray-600">Monitor and manage the Errand GO platform</p>
        </div>
        <Button color="primary" startContent={<Download className="w-4 h-4" />} onClick={handleExportReport}>
          Export Report
        </Button>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-blue-100 rounded-full">
              <Users className="w-6 h-6 text-blue-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Total Users</p>
              <p className="text-2xl font-bold">{stats.totalUsers.toLocaleString()}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-green-100 rounded-full">
              <Users className="w-6 h-6 text-green-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Errand Boys</p>
              <p className="text-2xl font-bold">{stats.totalErrandBoys}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-purple-100 rounded-full">
              <TrendingUp className="w-6 h-6 text-purple-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Total Tasks</p>
              <p className="text-2xl font-bold">{stats.totalTasks.toLocaleString()}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-yellow-100 rounded-full">
              <DollarSign className="w-6 h-6 text-yellow-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Total Revenue</p>
              <p className="text-2xl font-bold">${stats.totalRevenue.toLocaleString()}</p>
            </div>
          </CardBody>
        </Card>
      </div>

      <div className="grid lg:grid-cols-3 gap-6">
        {/* Recent Tasks */}
        <div className="lg:col-span-2">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between">
              <h2 className="text-xl font-semibold">Recent Tasks</h2>
              <Button size="sm" variant="flat" startContent={<Eye className="w-4 h-4" />}>
                View All
              </Button>
            </CardHeader>
            <CardBody>
              <Table aria-label="Recent tasks table">
                <TableHeader>
                  <TableColumn>TASK</TableColumn>
                  <TableColumn>USER</TableColumn>
                  <TableColumn>ERRAND BOY</TableColumn>
                  <TableColumn>STATUS</TableColumn>
                  <TableColumn>AMOUNT</TableColumn>
                </TableHeader>
                <TableBody>
                  {recentTasks.map((task: any) => (
                    <TableRow key={task.id}>
                      <TableCell>{task.title}</TableCell>
                      <TableCell>{task.user}</TableCell>
                      <TableCell>{task.errandBoy}</TableCell>
                      <TableCell>
                        <Chip color={getStatusColor(task.status)} variant="flat" size="sm">
                          {task.status}
                        </Chip>
                      </TableCell>
                      <TableCell>${task.amount}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </CardBody>
          </Card>
        </div>

        {/* System Health */}
        <div className="space-y-6">
          <Card>
            <CardHeader>
              <h2 className="text-xl font-semibold">System Health</h2>
            </CardHeader>
            <CardBody>
              <div className="space-y-4">
                <div>
                  <div className="flex justify-between text-sm mb-1">
                    <span>Active Users</span>
                    <span>
                      {stats.activeUsers}/{stats.totalUsers}
                    </span>
                  </div>
                  <Progress value={(stats.activeUsers / stats.totalUsers) * 100} color="success" size="sm" />
                </div>
                <div>
                  <div className="flex justify-between text-sm mb-1">
                    <span>Task Completion Rate</span>
                    <span>{stats.completionRate}%</span>
                  </div>
                  <Progress value={stats.completionRate} color="primary" size="sm" />
                </div>
              </div>
            </CardBody>
          </Card>

          {/* Quick Actions */}
          <Card>
            <CardHeader>
              <h2 className="text-xl font-semibold">Quick Actions</h2>
            </CardHeader>
            <CardBody className="space-y-3">
              <Button variant="flat" className="w-full justify-start" startContent={<Users className="w-4 h-4" />}>
                Manage Users
              </Button>
              <Button variant="flat" className="w-full justify-start" startContent={<TrendingUp className="w-4 h-4" />}>
                View Analytics
              </Button>
              <Button
                variant="flat"
                className="w-full justify-start"
                startContent={<AlertTriangle className="w-4 h-4" />}
              >
                System Alerts
              </Button>
              <Button
                variant="flat"
                className="w-full justify-start"
                startContent={<Download className="w-4 h-4" />}
                onClick={handleExportReport}
              >
                Export Reports
              </Button>
            </CardBody>
          </Card>
        </div>
      </div>
    </div>
  )
}
